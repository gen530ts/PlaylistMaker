package gen.test.android.playlistmaker.data.player.impl

import android.media.MediaPlayer
import gen.test.android.playlistmaker.domain.player.MediaPlayerWrapper
import gen.test.android.playlistmaker.domain.player.models.PlayerState

class MediaPlayerWrapperImpl(private val mediaPlayer: MediaPlayer) : MediaPlayerWrapper {

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
        //mediaPlayer.

        mediaPlayer.setOnPreparedListener {
            state = PlayerState.STATE_PREPARED
            cbPrepared()
        }
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnCompletionListener {
            state = PlayerState.STATE_COMPLETED
            it.stop()
            state = PlayerState.STATE_STOPPED
            it.prepareAsync()
            cbCompletion()
        }
    }

    override fun play() {
        state = PlayerState.STATE_PLAYING
        mediaPlayer.start()

    }

    override fun pause() {
        mediaPlayer.pause()
        state = PlayerState.STATE_PAUSED
    }
}