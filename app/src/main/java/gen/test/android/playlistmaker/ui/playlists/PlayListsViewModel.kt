package gen.test.android.playlistmaker.ui.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.launch

class PlayListsViewModel(
    private val plistInteractor: PlistInteractor
) : ViewModel() {
    private val liveData = MutableLiveData<ScreenState<List<Plist>>>(ScreenState.Warning)


    fun observeData(): LiveData<ScreenState<List<Plist>>> = liveData

    fun findPlaylists() {
        viewModelScope.launch {
            plistInteractor.getAllPlists().collect {
                if (it.isEmpty()) {
                    liveData.postValue(ScreenState.Warning)
                } else {
                    liveData.postValue(ScreenState.Success(it))
                }
            }
        }
    }
}