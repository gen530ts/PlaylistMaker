package gen.test.android.playlistmaker.ui.search.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gen.test.android.playlistmaker.App
import gen.test.android.playlistmaker.creator.Creator
import gen.test.android.playlistmaker.domain.search.TrackSearchInteractor
import gen.test.android.playlistmaker.domain.search.model.SearchTrackState
import gen.test.android.playlistmaker.domain.search.model.TrackSearch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }

    private val interactorHistory=Creator.provideHistoryInteractor((application as App).sharedPrefs)
    private val interactorSearch = Creator.provideSearchInteractor(getApplication())
    private val handler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<SearchTrackState>()
    fun observeState(): LiveData<SearchTrackState> = stateLiveData
    private fun renderState(state: SearchTrackState) {
        stateLiveData.postValue(state)
    }

    fun search(str: String) {
        renderState(SearchTrackState.Loading)
        interactorSearch.searchTrack(str, object : TrackSearchInteractor.TrackSearchConsumer {
            override fun consume(foundMovies: ArrayList<TrackSearch>?, isError: Boolean) {
                val movies = ArrayList<TrackSearch>()
                if (foundMovies != null) {
                    movies.addAll(foundMovies)
                }
                if (foundMovies != null) {
                    when {
                        isError -> renderState(SearchTrackState.Error)
                        foundMovies.isEmpty() -> renderState(SearchTrackState.Empty)
                        else -> {
                            renderState(SearchTrackState.Content(movies = movies))
                        }
                    }
                }
            }

        })
    }
    fun historyAdd(track:TrackSearch){
        interactorHistory.add(track)
    }
    fun historyRead():ArrayList<TrackSearch>{
       return interactorHistory.read()
    }
    fun historyClear(){
        interactorHistory.clear()
    }
}