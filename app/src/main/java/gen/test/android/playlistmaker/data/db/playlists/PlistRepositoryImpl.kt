package gen.test.android.playlistmaker.data.db.playlists

import gen.test.android.playlistmaker.data.db.TrackDatabase
import gen.test.android.playlistmaker.data.db.tracksplists.TrackPlDbConvertor
import gen.test.android.playlistmaker.domain.db.PlistRepository
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PlistRepositoryImpl(
    private val trackDatabase: TrackDatabase,
    private val plistDbConvertor: PlistDbConvertor,
    private val trackPlDbConvertor: TrackPlDbConvertor,
    private val imageHandler: ImageHandler
) : PlistRepository {
    override suspend fun addPlist(plist: Plist) {
        val plistDb = plistDbConvertor.map(plist)
        if (plist.imageUri != null) {
            imageHandler.saveImage(plist.imageUri, plistDb)
        }
        trackDatabase.plistDao().insertPlist(plistDb)
    }

    override suspend fun addTrackToPlists(track: Track) {
        trackDatabase.trackPlistDao().insertTrack(trackPlDbConvertor.map(track))
    }

    override suspend fun updatePlist(plist: Plist) {
        val plistDb = plistDbConvertor.map(plist)
        trackDatabase.plistDao().updatePlist(plistDb.name, plistDb.idTracks)
    }

    override suspend fun getAllPlists(): List<Plist> {
        val plists = trackDatabase.plistDao().getAllPlists().map { pl -> plistDbConvertor.map(pl)
        }
        return plists
    }





    override fun getAllPlistsFlow(): Flow<List<Plist>> {
       return trackDatabase.plistDao().getAllPlistsFlow().map { pl -> pl.map { item ->
           plistDbConvertor.map(item) } }
    }


    override fun getTracksByPlistId(id: Long): Flow<List<Track>> = flow {
        val plist = plistDbConvertor.map(trackDatabase.plistDao().getPlistById(id))
        val idTracks = plist.idTracks
        val tracks = listOf<Track>()
        idTracks.forEach { idT ->
            val track = trackPlDbConvertor.map(trackDatabase.trackPlistDao().getTrackPlistById(idT))
            tracks.plus(track)
        }
        emit(tracks)
    }

    override suspend fun getAllTracks(): List<Track> {
      return  trackDatabase.trackPlistDao().getAllTracks().map { tr -> trackPlDbConvertor.map(tr) }
    }

    override suspend fun getPlistById(id: Long): Plist {
        return plistDbConvertor.map(trackDatabase.plistDao().getPlistById(id))
    }

    override suspend fun getPlistByIdFlow(id: Long): Flow<Plist> {
        return trackDatabase.plistDao().getPlistByIdFlow(id).map { pl ->
            if(pl!=null){
            plistDbConvertor.map(pl)
        }else Plist(name = "")
             }
    }

    override suspend fun delTrackPlistById(id: Int) {
        trackDatabase.trackPlistDao().delTrackPlistById(id)
    }

    override suspend fun delPlistById(id: Long) {
        trackDatabase.plistDao().delPlistById(id)
    }
}

   /* override suspend fun getPlistViewData(idPlist: Long): PlistViewScreen {
       return coroutineScope {
            val plist= async {plistDbConvertor.map(trackDatabase.plistDao().getPlistById
                (idPlist))}.await()
            val allTracks= async {trackDatabase.trackPlistDao().getAllTracks().map { tr->trackPlDbConvertor
                .map(tr) }}.await()
          // val plist=plistDef.await()
            PlistViewScreen(name=plist.name, description = plist.description)
        }*/

       // return PlistViewScreen(name=plist.)
        //val currentPlistTracks = listOf<Track>()
       // allTracks.forEach { tr-> if()}
      //  return PlistViewScreen(name="name")

