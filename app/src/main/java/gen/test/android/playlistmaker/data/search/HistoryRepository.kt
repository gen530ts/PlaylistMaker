package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.domain.search.model.TrackSearch

interface HistoryRepository {
    fun add(track: TrackSearch)
    fun read(): ArrayList<TrackSearch>
    fun clear()
}