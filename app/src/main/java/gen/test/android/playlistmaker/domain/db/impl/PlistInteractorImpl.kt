package gen.test.android.playlistmaker.domain.db.impl

import gen.test.android.playlistmaker.domain.db.PlistInteractor
import gen.test.android.playlistmaker.domain.db.PlistRepository
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.domain.models.PlistViewScreen
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.utils.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

        return plistRepository.getAllPlistsFlow()
    }

    override fun getTracksByPlistId(id: Long): Flow<List<Track>> {
        return plistRepository.getTracksByPlistId(id)
    }

    override suspend fun getPlistById(id: Long): Plist {
        return plistRepository.getPlistById(id)
    }



    override suspend fun getPlistViewDataFlow(id: Long): Flow<PlistViewScreen> {
       return plistRepository.getPlistByIdFlow(id).map { pl ->
           getPlistViewScreen(pl)

       }
    }

    override suspend fun delTrackInPlist(plistId: Long, trackId: Int) {
        plistRepository.delTrackInPlist(plistId,trackId)
    }

    override suspend fun delPlistById(idPlist: Long) {
        val plists=plistRepository.getAllPlists()
        val usedTracksId = mutableSetOf<Int>()
        val trackToDelete= mutableListOf<Int>()
        for(pl in plists){
            if(pl.id==idPlist) trackToDelete.addAll(pl.idTracks)
            else usedTracksId.addAll(pl.idTracks)
        }
        val iteratorId = trackToDelete.iterator()
        while (iteratorId.hasNext()) {
            val item = iteratorId.next()
            if (usedTracksId.contains(item)) {
                iteratorId.remove()
            }
        }
        plistRepository.delPlistById(idPlist)
        for(trackId in trackToDelete){
            plistRepository.delTrackPlistById(trackId)
        }
    }

    private suspend fun getPlistViewScreen(plist:Plist):PlistViewScreen{

            val tracksAll= plistRepository.getAllTracks()

            var numOfTracks=0
            var timeOfTracks=0

            val tracksInPlist=mutableListOf<Track>()
            for (track in tracksAll) {
                if (plist.idTracks.contains(track.trackId)) {
                    tracksInPlist.add(track)
                    numOfTracks++
                    timeOfTracks += track.trackTimeMillis
                }
            }

          return  PlistViewScreen(
              id=plist.id,
              name=plist.name,
              description = plist.description,
              imageUri = plist.imageUri,
              durationAllTracks = getStrParam(timeOfTracks,ParamPlist.Time),
              numberOfTracks = getStrParam(numOfTracks,ParamPlist.Track),
              tracks = tracksInPlist.sortedByDescending { it.createAt }.toList())


    }

    enum class ParamPlist{
        Track,Time
    }



   private fun getStrParam(num: Int,param:ParamPlist): String {

        val number = if(param==ParamPlist.Time) num/60000
        else num
        val i = number % 100
        val i1 = i % 10
        val i2 = i / 10
        val first= "$number "
        var pos= if ((i1 == 1) && (i2 != 1)) 0
        else if ((i1 > 1) && (i1 < 5) && (i2 != 1)) 1
        else 2

        if(param==ParamPlist.Time) pos+=3

        val second=when(pos){
            0->"трек"
            1->"трека"
            2->"треков"
            3->"минута"
            4->"минуты"
            5->"минут"
            else -> ""
        }
        return  "$first$second"
    }
}


/*       val plists=plistRepository.getAllPlists()
        var isTrackInOtherPlist=false
        for(pl in plists){
            if((pl.idTracks.contains(trackId)) && (pl.id!=plistId)) isTrackInOtherPlist=true
            if (pl.id==plistId){
                val tmpList = mutableListOf<Int>()
                tmpList.addAll(pl.idTracks)
                tmpList.remove(trackId)
                val plist=pl.copy(idTracks = tmpList)
                plistRepository.updatePlist(plist)
            }
        }
        if(!isTrackInOtherPlist) plistRepository.delTrackPlistById(trackId)*/



