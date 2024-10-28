package gen.test.android.playlistmaker.ui.player.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.domain.db.FavoriteInteractor
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.domain.player.GetTrackUseCase
import gen.test.android.playlistmaker.domain.player.PlayerInteractor
import gen.test.android.playlistmaker.domain.player.models.PlayerState
import gen.test.android.playlistmaker.ui.player.model.ModifyUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val UPDATE_UI = 300L

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val getTrack: GetTrackUseCase,
    private val favoriteInteractor: FavoriteInteractor
) :
    ViewModel() {

    private val trackLD = MutableLiveData<Track>()
    fun getTrackLD(): LiveData<Track> = trackLD

    private val modUI = MutableLiveData<ModifyUI>()
    fun getModUI(): LiveData<ModifyUI> = modUI

    private val isFavorite = MutableLiveData(false)
    fun getFavorite(): LiveData<Boolean> = isFavorite

    private var timerJob: Job? = null
    private var isStart = true
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(UPDATE_UI)
                modUI.value = ModifyUI.TimePlayTV(
                    Utils.millisToMmSs(
                        playerInteractor
                            .getCurrentPosition()
                    )
                )
            }
        }
    }

    fun onFavoriteClicked() {
        val track = trackLD.value
        if (track != null) {
            viewModelScope.launch {
                if (isFavorite.value == true) {
                    Log.d("FavC", "track.isFavorite")
                    favoriteInteractor.delTrackFavorite(track)
                } else {
                    Log.d("FavC", "track.isNotFavorite")
                    favoriteInteractor.addTrackFavorite(track)
                }

                //(isFavorite.value).also { isFavorite.value = !it }
                isFavorite.value = !isFavorite.value!!
                // trackLD.value=track.copy(isFavorite =! track.isFavorite)
                //isFavorite.value?.let { isFavorite.value = !it }
            }
        }
    }


    fun prepare(json: String) {
        trackLD.value = getTrack.execute(json)
        if (trackLD.value != null) {
            isFavorite.value = trackLD.value!!.isFavorite
        }

        /*val track = getTrack.execute(json)
        trackLD.value = TrackUI(
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl
        )*/
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.release()
    }


    fun preparePlayer(src: String) {
        if (isStart) {

            playerInteractor.preparePlayer(src, { modUI.value = ModifyUI.PlayBtn(true) }, {

                modUI.value = ModifyUI.PlayBtnImagePlay(true)
                // handler.removeCallbacks(updateUIRunnable)
                timerJob?.cancel()
                modUI.value = ModifyUI.TimePlayTV("0:00")
            })
            isStart = false
        }

        modUI.value = ModifyUI.TimePlayTV(
            Utils.millisToMmSs(
                playerInteractor
                    .getCurrentPosition()
            )
        )
    }

    private fun startPlayer() {
        playerInteractor.play {

            modUI.value = ModifyUI.PlayBtnImagePlay(false)
            //handler.post(updateUIRunnable)
            startTimer()
        }
    }

    fun pausePlayer() {
        playerInteractor.pause {

            modUI.value = ModifyUI.PlayBtnImagePlay(true)
            timerJob?.cancel()
        }
    }

    fun playbackControl() {
        when (playerInteractor.getState()) {
            PlayerState.STATE_PLAYING -> {
                pausePlayer()
            }
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                startPlayer()
            }
            else -> {}
        }
    }
}