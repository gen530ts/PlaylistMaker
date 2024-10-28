package gen.test.android.playlistmaker.data.search.impl

import gen.test.android.playlistmaker.data.db.TrackDatabase
import gen.test.android.playlistmaker.data.search.HistoryManager
import gen.test.android.playlistmaker.data.search.HistoryRepository
import gen.test.android.playlistmaker.data.search.model.TrackSearchDto
import gen.test.android.playlistmaker.domain.models.Track

class HistoryRepositoryImpl(
    private val historyManager: HistoryManager,
    private val trackDatabase: TrackDatabase,
) : HistoryRepository {
    override fun add(track: Track) {
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

    override suspend fun read(): ArrayList<Track> {
        val listFavorites = trackDatabase.trackDao().getTrackId()

        return historyManager.read().map {
            Track(
                it.trackName,
                it.artistName,
                it.trackTimeMillis,
                it.artworkUrl100,
                it.trackId,
                it.collectionName,
                it.releaseDate,
                it.primaryGenreName,
                it.country,
                it.previewUrl,
                isFavorite = listFavorites.contains(it.trackId)
            )
        } as ArrayList<Track>
    }

    override fun clear() {
        historyManager.clear()
    }
}