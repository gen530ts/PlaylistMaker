package gen.test.android.playlistmaker.data.db.playlists

import gen.test.android.playlistmaker.data.db.TrackDatabase
import gen.test.android.playlistmaker.data.db.tracksplists.TrackPlDbConvertor
import gen.test.android.playlistmaker.domain.db.PlistRepository
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlistRepositoryImpl(
    private val trackDatabase: TrackDatabase,
    private val plistDbConvertor: PlistDbConvertor,
    private val trackPlDbConvertor: TrackPlDbConvertor,
    private val imageHandler:ImageHandler
): PlistRepository {
    override suspend fun addPlist(plist: Plist) {
        val plistDb=plistDbConvertor.map(plist)
        if (plistDb.imagePath.isNotEmpty()){ imageHandler.saveImage(plistDb.imagePath,plistDb) }
        trackDatabase.plistDao().insertPlist(plistDb)
    }

    override suspend fun addTrackToPlists(track: Track) {
        trackDatabase.trackPlistDao().insertTrack(trackPlDbConvertor.map(track))
    }

    override suspend fun updatePlist(plist: Plist) {
        val plistDb=plistDbConvertor.map(plist)
        trackDatabase.plistDao().updatePlist(plistDb.name,plistDb.idTracks)
    }

    override fun getAllPlists(): Flow<List<Plist>> = flow {
        val plists = trackDatabase.plistDao().getAllPlists()
        emit(plists.map { pl -> plistDbConvertor.map(pl) }
        )
    }
}