package gen.test.android.playlistmaker.data.db.playlists

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "plist_table")
data class PlistDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
    val description: String,
    val imagePath: String,
    val idTracks: String,

)
