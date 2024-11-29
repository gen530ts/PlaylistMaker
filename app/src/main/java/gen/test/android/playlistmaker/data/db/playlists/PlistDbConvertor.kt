package gen.test.android.playlistmaker.data.db.playlists

import gen.test.android.playlistmaker.domain.models.Plist

class PlistDbConvertor {
    fun map(plist: PlistDB): Plist {
        val tracksIds= strToListInt(plist.idTracks)
        return Plist(
            name = plist.name,
            description = plist.description,
            imagePath = plist.imagePath,
            idTracks = tracksIds,
            tracksNumber = getStr(tracksIds.size)
        )
    }

    fun map(plist: Plist): PlistDB {
        return PlistDB(
            id = null,
            name = plist.name,
            description = plist.description,
            imagePath = plist.imagePath,
            idTracks = plist.idTracks.toString(),
        )
    }

    private fun getStr(num: Int): String {
        val i = num % 100
        val i1 = i % 10
        val i2 = i / 10
        val firstStr = num.toString()
        val secondStr = if ((i1 == 1) && (i2 != 1)) " трек"
        else if ((i1 > 1) && (i1 < 5) && (i2 != 1)) " трека"
        else " треков"
        return firstStr + secondStr
    }
    private fun strToListInt(str:String):List<Int>{
        if(str=="[]") return listOf()
        return str.removeSurrounding("[","]").replace(" ","").split(",").map { it.toInt() }
    }

}