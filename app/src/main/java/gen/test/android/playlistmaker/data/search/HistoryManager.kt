package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.data.search.model.TrackSearchDto

interface HistoryManager {
    fun add(track: TrackSearchDto)
    fun read(): ArrayList<TrackSearchDto>
    fun clear()
}