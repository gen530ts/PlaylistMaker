package gen.test.android.playlistmaker.domain.api

import gen.test.android.playlistmaker.domain.models.PlayerState

interface PlayerInteractor {
   var state: PlayerState
    fun getCurrentPosition(): Int
    fun release()
    fun preparePlayer(src: String, cbPrepared: ()-> Unit,  cbCompletion: ()-> Unit)
    fun play(callback: ()-> Unit)
    fun pause(callback: ()-> Unit)
}