package gen.test.android.playlistmaker.domain.db

import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteInteractor {
    suspend fun addTrackFavorite(track: Track)
    suspend fun delTrackFavorite(track: Track)
    fun getTrackFavorite(): Flow<List<Track>>
}