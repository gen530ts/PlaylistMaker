package gen.test.android.playlistmaker.domain.db

import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlistRepository {
    suspend fun addPlist(plist: Plist)
    suspend fun addTrackToPlists(track: Track)
    suspend fun updatePlist(plist: Plist)
    suspend fun getAllPlists(): List<Plist>
    fun getTracksByPlistId(id: Long): Flow<List<Track>>
    suspend fun getAllTracks():List<Track>
    fun getAllPlistsFlow(): Flow<List<Plist>>
    suspend fun getPlistById(id: Long):Plist
    suspend fun getPlistByIdFlow(id: Long):Flow<Plist>
    suspend fun delTrackPlistById(id: Int)
    suspend fun delPlistById(id: Long)
    suspend fun delTrackInPlist(plistId:Long,trackId:Int)

}