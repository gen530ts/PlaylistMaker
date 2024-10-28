package gen.test.android.playlistmaker.ui.media.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.db.FavoriteInteractor
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.launch

class FavTracksViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private val liveData = MutableLiveData<ScreenState<List<Track>>>(ScreenState.Warning)
    fun observeData(): LiveData<ScreenState<List<Track>>> = liveData

    fun findFavorites() {
        viewModelScope.launch {
            favoriteInteractor.getTrackFavorite().collect {
                if (it.isEmpty()) {
                    liveData.postValue(ScreenState.Warning)
                } else {
                    val temp = it.map { track -> track.copy(isFavorite = true) }.reversed()
                    liveData.postValue(ScreenState.Success(temp))
                }
            }
        }
    }
}