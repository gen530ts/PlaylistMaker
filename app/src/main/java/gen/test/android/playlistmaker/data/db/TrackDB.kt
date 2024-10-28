package gen.test.android.playlistmaker.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class TrackDB(
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

//@ColumnInfo(defaultValue = "(datetime('now')"))//""System.currentTimeMillis()
