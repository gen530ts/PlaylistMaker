package gen.test.android.playlistmaker.domain.player

import gen.test.android.playlistmaker.domain.player.models.PlayerState

interface PlayerInteractor {
   fun getState(): PlayerState
    fun getCurrentPosition(): Int
    fun release()
    fun preparePlayer(src: String, cbPrepared: ()-> Unit,  cbCompletion: ()-> Unit)
    fun play(callback: ()-> Unit)
    fun pause(callback: ()-> Unit)
}