package gen.test.android.playlistmaker.domain.search.model

import gen.test.android.playlistmaker.domain.models.Track

sealed interface SearchTrackState{
    object Default : SearchTrackState
    object Loading : SearchTrackState
    object Error : SearchTrackState
    object Empty : SearchTrackState
    data class Content(val movies: ArrayList<Track>) : SearchTrackState
    data class History(val movies: List<Track>) : SearchTrackState
}