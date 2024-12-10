package gen.test.android.playlistmaker.domain.db.impl

import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.db.PlistRepository
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.flow.Flow

class PlistInteractorImpl(private val plistRepository: PlistRepository): PlistInteractor {
    override suspend fun addPlist(plist: Plist) {
        plistRepository.addPlist(plist)
    }

    override suspend fun addTrackToPlist(plist: Plist, track: Track): ScreenState<Int> {

            val newPlist=plist.copy(idTracks = plist.idTracks.plus(track.trackId))
            plistRepository.updatePlist(newPlist)
            plistRepository.addTrackToPlists(track)
           return ScreenState.Success(1)

    }

    override fun getAllPlists(): Flow<List<Plist>> {
        return plistRepository.getAllPlists()
    }
}