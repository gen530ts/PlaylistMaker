package gen.test.android.playlistmaker.data.db.playlists

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlist(plist: PlistDB)
    @Query("UPDATE plist_table SET idTracks = :ids WHERE name = :namePlist")
    suspend fun updatePlist(namePlist: String,ids:String)
    @Query("SELECT * FROM plist_table")
    suspend fun getAllPlists(): List<PlistDB>
}