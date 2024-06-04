package gen.test.android.playlistmaker.ui.player.model

sealed class ModifyUI{
    data class TimePlayTV(val txt:String):ModifyUI()
    data class PlayBtn(val isEn:Boolean):ModifyUI()
    data class PlayBtnImagePlay(val isEn:Boolean):ModifyUI()
}
