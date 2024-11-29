package gen.test.android.playlistmaker.domain.models

data class Plist(
    val name: String,
    val description: String="",
    val imagePath: String="",
    val idTracks: List<Int> = listOf(),
    val tracksNumber: String="",
)
