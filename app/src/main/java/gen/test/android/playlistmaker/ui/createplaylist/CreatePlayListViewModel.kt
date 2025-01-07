package gen.test.android.playlistmaker.ui.createplaylist

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.launch

open class CreatePlayListViewModel (
    val plistInteractor: PlistInteractor
) : ViewModel() {
    val liveData = MutableLiveData<ScreenState<Plist>>(ScreenState.Warning)
    fun observeData(): LiveData<ScreenState<Plist>> = liveData

    fun addPlaylist(idPl:Long?,name:String,descr:String,imageUri: Uri?) {
        viewModelScope.launch {
            plistInteractor.addPlist(Plist(id=idPl,name=name, description = descr, imageUri =
            imageUri))
            val namePl=if(idPl==null) name else ""
            liveData.postValue(ScreenState.Success(Plist(name=namePl)))



        }
    }

    fun resetLd() {
        liveData.value=ScreenState.Warning
    }
}

