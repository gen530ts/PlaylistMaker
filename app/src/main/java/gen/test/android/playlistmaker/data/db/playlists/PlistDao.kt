package gen.test.android.playlistmaker.data.db.playlists

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlist(plist: PlistDB)
    @Query("UPDATE plist_table SET idTracks = :ids WHERE name = :namePlist")
    suspend fun updatePlist(namePlist: String,ids:String)
    @Query("SELECT * FROM plist_table")
    suspend fun getAllPlists(): List<PlistDB>
    @Query("SELECT * FROM plist_table")
    fun getAllPlistsFlow(): Flow<List<PlistDB>>
    @Query("SELECT * FROM plist_table WHERE id=:id")
    suspend fun getPlistById(id:Long): PlistDB
    @Query("SELECT * FROM plist_table WHERE id=:id")
    fun getPlistByIdFlow(id:Long): Flow <PlistDB?>
    @Query("DELETE FROM plist_table WHERE id = :idPl")
    suspend fun delPlistById(idPl: Long)

}