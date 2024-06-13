package gen.test.android.playlistmaker.domain.search.model

sealed interface SearchTrackState{

    object Loading : SearchTrackState
    object Error : SearchTrackState
    object Empty : SearchTrackState
    data class Content(val movies: ArrayList<TrackSearch>) : SearchTrackState
    data class History(val movies: List<TrackSearch>) : SearchTrackState
}