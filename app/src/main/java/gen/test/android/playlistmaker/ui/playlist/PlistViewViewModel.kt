package gen.test.android.playlistmaker.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.models.PlistViewScreen
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlistViewViewModel(
    private val playlistId: Long,
    private val plistInteractor: PlistInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private var playlistStateLiveData = MutableLiveData<PlistViewScreen>()
    fun observePlist(): LiveData<PlistViewScreen> = playlistStateLiveData


    init {
        viewModelScope.launch {
            plistInteractor.getPlistViewDataFlow(playlistId).collect {
                playlistStateLiveData.postValue(it)
            }
        }
    }

    fun delTrackInPlist(idTrack: Int) {
        viewModelScope.launch {
            plistInteractor.delTrackInPlist(playlistId, idTrack)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delPlist() {
        GlobalScope.launch {
            val plist = playlistStateLiveData.value
            if ((plist != null)&&(plist.id!=null)) {
                plistInteractor.delPlistById(plist.id)
            }
        }
    }

    fun sharePlist() {
        val plist = playlistStateLiveData.value
        if (plist != null) {
            sharingInteractor.sharePlist(plist)
        }
    }
}


