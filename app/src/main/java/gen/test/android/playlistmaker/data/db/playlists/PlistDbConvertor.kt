package gen.test.android.playlistmaker.data.db.playlists

import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import gen.test.android.playlistmaker.domain.models.Plist
import java.io.File

class PlistDbConvertor(private val context: Context) {
    fun map(plist: PlistDB): Plist {
        val tracksIds = strToListInt(plist.idTracks)
        val iU = if (plist.imagePath.isNotEmpty()) {
            val filePath = File(
                context.getExternalFilesDir(
                    Environment.DIRECTORY_PICTURES
                ), "playlistmaker_album"
            )
            val file = File(filePath, plist.imagePath)
            file.toUri()
        } else null
        return Plist(
            id = plist.id,
            name = plist.name,
            description = plist.description,
            imagePath = plist.imagePath,
            idTracks = tracksIds,
            tracksNumber = getStr(tracksIds.size),
            imageUri = iU
        )
    }

    fun map(plist: Plist): PlistDB {
        return PlistDB(
            id = plist.id,
            name = plist.name,
            description = plist.description,
            imagePath = if (plist.imageUri != null) {
                plist.name + plist.description + ".jpg"
            } else {
                ""
            },

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

    private fun strToListInt(str: String): List<Int> {
        if (str == "[]") return listOf()
        return str.removeSurrounding("[", "]").replace(" ", "").split(",").map { it.toInt() }
    }

}