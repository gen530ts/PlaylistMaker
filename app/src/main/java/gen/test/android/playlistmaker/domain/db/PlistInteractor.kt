package gen.test.android.playlistmaker.domain.db

import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.PlistViewScreen
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.flow.Flow

interface PlistInteractor {
    suspend fun addPlist(plist: Plist)
    suspend fun addTrackToPlist(plist: Plist,track: Track):ScreenState<Int>
    fun getAllPlists(): Flow<List<Plist>>
    fun getTracksByPlistId(id: Long): Flow<List<Track>>
    suspend fun getPlistById(id:Long):Plist
   // suspend fun getPlistViewData(id:Long):PlistViewScreen
    suspend fun getPlistViewDataFlow(id:Long):Flow<PlistViewScreen>
    suspend fun delTrackInPlist(plistId:Long,trackId:Int)
    suspend fun delPlistById(idPlist:Long)
}