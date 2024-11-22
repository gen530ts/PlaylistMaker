package gen.test.android.playlistmaker.ui.createplaylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.launch

class CreatePlayListViewModel (
    private val plistInteractor: PlistInteractor
) : ViewModel() {
    private val liveData = MutableLiveData<ScreenState<String>>(ScreenState.Warning)
    fun observeData(): LiveData<ScreenState<String>> = liveData

    fun addPlaylist(name:String,descr:String,imagePath: String) {
        viewModelScope.launch {
            plistInteractor.addPlist(Plist(name=name, description = descr, imagePath = imagePath))
            liveData.postValue(ScreenState.Success(name))
        }
    }
    fun resetLd() {
        liveData.value=ScreenState.Warning
    }
}

