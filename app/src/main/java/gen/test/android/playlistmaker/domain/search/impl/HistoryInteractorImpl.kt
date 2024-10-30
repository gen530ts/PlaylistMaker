package gen.test.android.playlistmaker.domain.search.impl

import gen.test.android.playlistmaker.data.search.HistoryRepository
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.domain.search.HistoryInteractor

class HistoryInteractorImpl(private val historyRepository: HistoryRepository): HistoryInteractor {
    override fun add(track: Track) {
        historyRepository.add(track)
    }

    override suspend fun read(): ArrayList<Track> {
        return historyRepository.read()
    }

    override fun clear() {
        historyRepository.clear()
    }
}