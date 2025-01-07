package gen.test.android.playlistmaker.ui.editplaylist

import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.ui.createplaylist.CreatePlayListViewModel
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.launch

class EditPlayListViewModel (
    private val playlistId: Long,
    plistInteractor: PlistInteractor
) : CreatePlayListViewModel(plistInteractor) {

    fun initPlistInfo(){
        viewModelScope.launch {
            val plist = plistInteractor.getPlistById(playlistId)
            liveData.postValue(ScreenState.Success(plist))
        }
    }
}