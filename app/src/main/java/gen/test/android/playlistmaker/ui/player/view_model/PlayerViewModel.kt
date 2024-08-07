package gen.test.android.playlistmaker.ui.player.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.domain.player.GetTrackUseCase
import gen.test.android.playlistmaker.domain.player.PlayerInteractor
import gen.test.android.playlistmaker.domain.player.models.PlayerState
import gen.test.android.playlistmaker.ui.player.model.ModifyUI
import gen.test.android.playlistmaker.ui.player.model.TrackUI

private const val UPDATE_UI = 300L

class PlayerViewModel(private val playerInteractor: PlayerInteractor,private val getTrack: GetTrackUseCase) :
    ViewModel() {



    private val trackLD = MutableLiveData<TrackUI>()
    fun getTrackLD(): LiveData<TrackUI> = trackLD

    private val modUI = MutableLiveData<ModifyUI>()
    fun getModUI(): LiveData<ModifyUI> = modUI

    private var isStart=true


    fun prepare(json: String) {
        val track = getTrack.execute(json)
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
        )
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.release()
    }

    val handler = Handler(Looper.getMainLooper())
    val updateUIRunnable = object : Runnable {
        override fun run() {
            
            modUI.value = ModifyUI.TimePlayTV(
                Utils.millisToMmSs(
                    playerInteractor
                        .getCurrentPosition()
                )
            )
            handler.postDelayed(this, UPDATE_UI)
        }
    }

    fun preparePlayer(src: String) {
        if (isStart) {
            
            playerInteractor.preparePlayer(src, { modUI.value = ModifyUI.PlayBtn(true) }, {
                
                modUI.value = ModifyUI.PlayBtnImagePlay(true)
                handler.removeCallbacks(updateUIRunnable)
                
                
                modUI.value = ModifyUI.TimePlayTV("0:00")
            })
            isStart=false
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
            handler.post(updateUIRunnable)
        }
    }

    fun pausePlayer() {
        playerInteractor.pause {
            
            modUI.value = ModifyUI.PlayBtnImagePlay(true)
            handler.removeCallbacks(updateUIRunnable)
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