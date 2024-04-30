package gen.test.android.playlistmaker.data

import android.media.MediaPlayer
import android.util.Log
import gen.test.android.playlistmaker.domain.api.Player
import gen.test.android.playlistmaker.domain.models.PlayerState

class PlayerImpl : Player {
    private val mediaPlayer = MediaPlayer()
    override var state: PlayerState = PlayerState.STATE_DEFAULT

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun preparePlayer(
        src: String, cbPrepared: () -> Unit,
        cbCompletion: () -> Unit
    ) {
        Log.d("mylog", "src=$src ")
        mediaPlayer.setDataSource(src)
        mediaPlayer.prepare()
        mediaPlayer.setOnPreparedListener {
            state = PlayerState.STATE_PREPARED
            cbPrepared()
        }
        mediaPlayer.setOnCompletionListener {
            state = PlayerState.STATE_PREPARED
            cbCompletion()
        }
    }

    override fun play() {
        mediaPlayer.start()
        state = PlayerState.STATE_PLAYING
    }

    override fun pause() {
        mediaPlayer.pause()
        state = PlayerState.STATE_PAUSED
    }
}