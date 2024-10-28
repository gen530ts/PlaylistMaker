package gen.test.android.playlistmaker.domain.db.impl

import gen.test.android.playlistmaker.domain.db.FavoriteInteractor
import gen.test.android.playlistmaker.domain.db.FavoriteRepository
import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoriteInteractorImpl(
    private val favoriteRepository: FavoriteRepository): FavoriteInteractor {
    override suspend fun addTrackFavorite(track: Track) {
        favoriteRepository.addTrackFavorite(track)
    }

    override suspend fun delTrackFavorite(track: Track) {
        favoriteRepository.delTrackFavorite(track)
    }

    override fun getTrackFavorite(): Flow<List<Track>> {
        return favoriteRepository.getTrackFavorite()
    }


}