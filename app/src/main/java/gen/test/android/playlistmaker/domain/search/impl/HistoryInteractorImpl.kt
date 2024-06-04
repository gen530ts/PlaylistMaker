package gen.test.android.playlistmaker.domain.search.impl

import gen.test.android.playlistmaker.data.search.HistoryRepository
import gen.test.android.playlistmaker.domain.search.HistoryInteractor
import gen.test.android.playlistmaker.domain.search.model.TrackSearch

class HistoryInteractorImpl(private val historyRepository: HistoryRepository): HistoryInteractor {
    override fun add(track: TrackSearch) {
        historyRepository.add(track)
    }

    override fun read(): ArrayList<TrackSearch> {
        return historyRepository.read()
    }

    override fun clear() {
        historyRepository.clear()
    }
}