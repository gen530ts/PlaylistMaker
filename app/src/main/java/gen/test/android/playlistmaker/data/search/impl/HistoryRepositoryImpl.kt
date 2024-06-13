package gen.test.android.playlistmaker.data.search.impl

import gen.test.android.playlistmaker.data.search.HistoryManager
import gen.test.android.playlistmaker.data.search.HistoryRepository
import gen.test.android.playlistmaker.data.search.model.TrackSearchDto
import gen.test.android.playlistmaker.domain.search.model.TrackSearch

class HistoryRepositoryImpl(private val historyManager: HistoryManager) : HistoryRepository {
    override fun add(track: TrackSearch) {
        historyManager.add(
            TrackSearchDto(
                track.trackName,
                track.artistName,
                track.trackTimeMillis,
                track.artworkUrl100,
                track.trackId,
                track.collectionName,
                track.releaseDate,
                track.primaryGenreName,
                track.country,
                track.previewUrl
            )
        )
    }

    override fun read(): ArrayList<TrackSearch> {
        return historyManager.read().map { TrackSearch(
            it.trackName,
            it.artistName,
            it.trackTimeMillis,
            it.artworkUrl100,
            it.trackId,
            it.collectionName,
            it.releaseDate,
            it.primaryGenreName,
            it.country,
            it.previewUrl
        ) } as ArrayList<TrackSearch>
    }

    override fun clear() {
        historyManager.clear()
    }
}