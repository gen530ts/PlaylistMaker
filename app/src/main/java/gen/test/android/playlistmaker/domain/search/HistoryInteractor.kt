package gen.test.android.playlistmaker.domain.search

import gen.test.android.playlistmaker.domain.models.Track

interface HistoryInteractor {
    fun add(track: Track)
   suspend fun read(): ArrayList<Track>
    fun clear()
}