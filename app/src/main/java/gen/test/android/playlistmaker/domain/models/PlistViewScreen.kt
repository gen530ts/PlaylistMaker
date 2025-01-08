package gen.test.android.playlistmaker.domain.models

import android.net.Uri

data class PlistViewScreen(
    val id: Long?,
    val name:String,
    val description:String="",
    val imageUri:Uri?=null,
    val durationAllTracks:String="0 минут",
    val numberOfTracks:String="0 треков",
    val tracks:List<Track> = listOf()
)
