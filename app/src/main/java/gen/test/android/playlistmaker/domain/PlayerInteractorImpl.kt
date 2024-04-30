package gen.test.android.playlistmaker.domain

import gen.test.android.playlistmaker.domain.api.Player
import gen.test.android.playlistmaker.domain.api.PlayerInteractor
import gen.test.android.playlistmaker.domain.models.PlayerState

class PlayerInteractorImpl(
    private val player: Player
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
        player.pause()
        callback()
    }
}