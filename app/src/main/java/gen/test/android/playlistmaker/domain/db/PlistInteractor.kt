package gen.test.android.playlistmaker.domain.db

import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.flow.Flow

interface PlistInteractor {
    suspend fun addPlist(plist: Plist)
    suspend fun addTrackToPlist(plist: Plist,track: Track):ScreenState<Int>
    fun getAllPlists(): Flow<List<Plist>>
}