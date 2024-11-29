package gen.test.android.playlistmaker.data.db.tracksplists

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "track_plist_table")
data class TrackPlistDB (
    @PrimaryKey
    val  trackId: Int,
    val artworkUrl100: String,
    val trackName: String,
    val artistName: String,
    val collectionName:String?,
    val releaseDate:String?,
    val primaryGenreName:String,
    val country:String,
    val trackTime: String,
    val previewUrl:String?,
    val create_at : Long
        )