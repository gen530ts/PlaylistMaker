package gen.test.android.playlistmaker.ui.media.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gen.test.android.playlistmaker.utils.ScreenState

class PlayListsViewModel : ViewModel() {
    private val liveData = MutableLiveData<ScreenState<String>>(ScreenState.Warning)


    fun observeData(): LiveData<ScreenState<String>> = liveData
}