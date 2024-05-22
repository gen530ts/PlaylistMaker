package gen.test.android.playlistmaker.domain.player

import gen.test.android.playlistmaker.domain.player.models.PlayerState

interface MediaPlayerWrapper {
    var state: PlayerState
    fun getCurrentPosition(): Int
    fun release()
    fun preparePlayer(
        src: String, cbPrepared: () -> Unit,
        cbCompletion: () -> Unit
    )
    fun play()
    fun pause()
}