package gen.test.android.playlistmaker.data.player.models

data class TrackDto (
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String,
    val  trackId: Int,
    val collectionName:String?,
    val releaseDate:String?,
    val primaryGenreName:String,
    val country:String,
    val previewUrl:String?,
    val isFavorite:Boolean
)