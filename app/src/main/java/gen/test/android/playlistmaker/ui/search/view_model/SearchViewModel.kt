package gen.test.android.playlistmaker.ui.search.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.domain.search.HistoryInteractor
import gen.test.android.playlistmaker.domain.search.TrackSearchInteractor
import gen.test.android.playlistmaker.domain.search.model.SearchTrackState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SEARCH_DEBOUNCE_DELAY = 2000L

class SearchViewModel(
    private val interactorHistory: HistoryInteractor, private val
    interactorSearch: TrackSearchInteractor
) : ViewModel() {

    private var searchTxt = ""
    private val stateLiveData = MutableLiveData<SearchTrackState>()
    fun observeState(): LiveData<SearchTrackState> = stateLiveData
    private fun renderState(state: SearchTrackState) {
        stateLiveData.postValue(state)
    }

    private var searchJob: Job? = null

    fun searchDebounce(str: String) {
        if (str == this.searchTxt) return
        searchJob?.cancel()
        this.searchTxt = str
        if (str.length > 2) {


            searchJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                search(searchTxt)
            }
        }
    }

    fun search(str: String) {

        renderState(SearchTrackState.Loading)
        viewModelScope.launch {
            interactorSearch
                .searchTrack(str)
                .collect { pair ->
                    if (pair.second) {
                        renderState(SearchTrackState.Error)
                    } else if ((pair.first != null)&& (pair.first!!.isNotEmpty())) {
                        val tracks = ArrayList<Track>()
                        tracks.addAll(pair.first!!)
                        renderState(SearchTrackState.Content(movies = tracks))
                    } else {
                        renderState(SearchTrackState.Empty)
                    }

                }
        }
    }


    fun historyAdd(track: Track) {
        interactorHistory.add(track)
    }

    fun historyRead() {
        viewModelScope.launch {
           val lHistory= interactorHistory.read()
            renderState(SearchTrackState.History(lHistory))
        }
    }

    fun historyClear() {
        interactorHistory.clear()
        renderState(SearchTrackState.History(arrayListOf()))
    }

    override fun onCleared() {
        searchJob?.cancel()
    }
}



