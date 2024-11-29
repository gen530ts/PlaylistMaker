package gen.test.android.playlistmaker.domain.player.impl

import gen.test.android.playlistmaker.domain.player.MediaPlayerWrapper
import gen.test.android.playlistmaker.domain.player.PlayerInteractor
import gen.test.android.playlistmaker.domain.player.models.PlayerState

class PlayerInteractorImpl(
    private val player: MediaPlayerWrapper
) : PlayerInteractor {
    override fun getState(): PlayerState {
        return player.state
    }


    override fun getCurrentPosition(): Int {
        return player.getCurrentPosition()
    }

    override fun release() {
        player.release()
    }

    override fun preparePlayer(
        src: String, cbPrepared: () -> Unit,
        cbCompletion: () -> Unit
    ) {
        player.preparePlayer(src,cbPrepared,cbCompletion)
    }

    override fun play(callback: () -> Unit) {
        player.play()
        callback()
    }

    override fun pause(callback: () -> Unit) {
        if(player.state==PlayerState.STATE_PLAYING){
            player.pause()
            callback()
        }

    }
}