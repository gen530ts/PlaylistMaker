package gen.test.android.playlistmaker.domain.models

import android.net.Uri

data class Plist(
    val id:Long?=null,
    val name: String,
    val description: String="",
    val imagePath: String="",
    val idTracks: List<Int> = listOf(),
    val tracksNumber: String="",
    val imageUri: Uri?=null
)
