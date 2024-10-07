package gen.test.android.playlistmaker.ui.media.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gen.test.android.playlistmaker.utils.ScreenState

class FavTracksViewModel : ViewModel() {
    private val liveData = MutableLiveData<ScreenState<Int>>(ScreenState.Warning)


    fun observeData(): LiveData<ScreenState<Int>> = liveData
}