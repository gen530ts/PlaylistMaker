package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.domain.models.Track

interface HistoryRepository {
    fun add(track: Track)
  suspend  fun read(): ArrayList<Track>
    fun clear()
}