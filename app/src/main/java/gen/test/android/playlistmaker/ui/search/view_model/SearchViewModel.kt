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

        
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }

    private val searchRunnable = Runnable { search(searchTxt) }

    
    private var searchTxt = ""
    private val interactorHistory =
        Creator.provideHistoryInteractor((application as App).sharedPrefs)
    private val interactorSearch = Creator.provideSearchInteractor(getApplication())
    private val handler = Handler(Looper.getMainLooper())
    private val stateLiveData = MutableLiveData<SearchTrackState>()
    fun observeState(): LiveData<SearchTrackState> = stateLiveData
    private fun renderState(state: SearchTrackState) {
        stateLiveData.postValue(state)
    }

    fun searchDebounce(str: String) {
        if (str == this.searchTxt) return
        handler.removeCallbacks(searchRunnable)
        this.searchTxt = str
        if (str.length > 2) {
            
            handler.postDelayed(
                searchRunnable,
                SEARCH_DEBOUNCE_DELAY
            )
        }
    }

    fun search(str: String) {
        
        renderState(SearchTrackState.Loading)
        interactorSearch.searchTrack(str, object : TrackSearchInteractor.TrackSearchConsumer {
            override fun consume(foundTracks: ArrayList<TrackSearch>?, isError: Boolean) {
                val tracks = ArrayList<TrackSearch>()
                if (foundTracks != null) {
                    tracks.addAll(foundTracks)
                }
                if (foundTracks != null) {
                    when {
                        isError -> renderState(SearchTrackState.Error)
                        foundTracks.isEmpty() -> renderState(SearchTrackState.Empty)
                        else -> {
                            renderState(SearchTrackState.Content(movies = tracks))
                        }
                    }
                }
            }

        })
    }

    fun historyAdd(track: TrackSearch) {
        interactorHistory.add(track)
    }

    fun historyRead(): ArrayList<TrackSearch> {
        return interactorHistory.read()
    }

    fun historyClear() {
        interactorHistory.clear()
    }

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
    }
}