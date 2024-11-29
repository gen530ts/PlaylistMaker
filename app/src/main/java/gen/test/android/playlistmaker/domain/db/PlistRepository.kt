package gen.test.android.playlistmaker.domain.db

import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlistRepository {
    suspend fun addPlist(plist: Plist)
    suspend fun addTrackToPlists(track: Track)
    suspend fun updatePlist(plist: Plist)
    fun getAllPlists(): Flow<List<Plist>>
}