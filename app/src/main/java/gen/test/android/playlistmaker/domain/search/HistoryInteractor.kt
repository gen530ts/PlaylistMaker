package gen.test.android.playlistmaker.domain.search

import gen.test.android.playlistmaker.domain.search.model.TrackSearch

interface HistoryInteractor {
    fun add(track: TrackSearch)
    fun read(): ArrayList<TrackSearch>
    fun clear()
}