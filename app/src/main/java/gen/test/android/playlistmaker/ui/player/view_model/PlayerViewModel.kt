package gen.test.android.playlistmaker.ui.player.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.domain.db.FavoriteInteractor
import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.domain.player.GetTrackUseCase
import gen.test.android.playlistmaker.domain.player.PlayerInteractor
import gen.test.android.playlistmaker.domain.player.models.PlayerState
import gen.test.android.playlistmaker.ui.player.model.ModifyUI
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val UPDATE_UI = 300L

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val getTrack: GetTrackUseCase,
    private val favoriteInteractor: FavoriteInteractor,
    private val plistInteractor: PlistInteractor
) :
    ViewModel() {

    private val trackLD = MutableLiveData<Track>()
    fun getTrackLD(): LiveData<Track> = trackLD

    private val modUI = MutableLiveData<ModifyUI>()
    fun getModUI(): LiveData<ModifyUI> = modUI

    private val isFavorite = MutableLiveData(false)
    fun getFavorite(): LiveData<Boolean> = isFavorite

    private val playLists = MutableLiveData<ScreenState<List<Plist>>>(ScreenState.Warning)
    fun observeData(): LiveData<ScreenState<List<Plist>>> = playLists

    private val addTrackPlist = MutableLiveData<ScreenState<Int>>(ScreenState.Warning)
    fun observeTrackToPlist(): LiveData<ScreenState<Int>> = addTrackPlist
    fun resetAddTrackToPlist() { addTrackPlist.value = ScreenState.Warning}
    fun addTrackToPlist(plist:Plist) {
        val tr=trackLD.value
        if(tr!=null){
            if(plist.idTracks.contains(tr.trackId)){
                addTrackPlist.value=ScreenState.Success(0)
            }else{
                viewModelScope.launch {
                    val result= plistInteractor.addTrackToPlist(plist,tr)
                    addTrackPlist.postValue(result)
                }
            }

        }
    }

    private var timerJob: Job? = null
    private var isStart = true

    fun findPlaylists() {
        viewModelScope.launch {
            plistInteractor.getAllPlists().collect {
                if (it.isEmpty()) {
                    playLists.postValue(ScreenState.Warning)
                } else {
                    playLists.postValue(ScreenState.Success(it))
                }
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(UPDATE_UI)
                val gcp=playerInteractor.getCurrentPosition()
                val tst=Utils.millisToMmSs(gcp

                )
                modUI.value = ModifyUI.TimePlayTV(tst)
            }
        }
    }

    fun onFavoriteClicked() {
        val track = trackLD.value
        if (track != null) {
            viewModelScope.launch {
                if (isFavorite.value == true) {
                    favoriteInteractor.delTrackFavorite(track)
                } else {
                    favoriteInteractor.addTrackFavorite(track)
                }
                isFavorite.value = !isFavorite.value!!
            }
        }
    }


    fun prepare(json: String) {
        trackLD.value = getTrack.execute(json)
        if (trackLD.value != null) {
            isFavorite.value = trackLD.value!!.isFavorite
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.release()
    }


    fun preparePlayer(src: String) {
        if (isStart) {
            playerInteractor.preparePlayer(src, { modUI.value = ModifyUI.PlayBtn(true) }, {
                modUI.value = ModifyUI.PlayBtnImagePlay(true)
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