package gen.test.android.playlistmaker.data.db


import gen.test.android.playlistmaker.domain.db.FavoriteRepository
import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(
    private val trackDatabase: TrackDatabase,
    private val trackDbConvertor: TrackDbConvertor,
) : FavoriteRepository {
    override fun getTrackFavorite(): Flow<List<Track>> = flow {
        val tracks = trackDatabase.trackDao().getTracks()
        emit(tracks.map { tr -> trackDbConvertor.map(tr) }
        )
    }

    override suspend fun addTrackFavorite(track: Track) {
        trackDatabase.trackDao().insertTrack(trackDbConvertor.map(track))

    }

    override suspend fun delTrackFavorite(track: Track) {
        trackDatabase.trackDao().deleteTrack(trackDbConvertor.map(track).trackId)
    }


}

