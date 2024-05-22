package gen.test.android.playlistmaker.data.player.impl

import android.media.MediaPlayer
import gen.test.android.playlistmaker.domain.player.MediaPlayerWrapper
import gen.test.android.playlistmaker.domain.player.models.PlayerState

class MediaPlayerWrapperImpl : MediaPlayerWrapper {
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
        mediaPlayer.setDataSource(src)
        mediaPlayer.prepareAsync()
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