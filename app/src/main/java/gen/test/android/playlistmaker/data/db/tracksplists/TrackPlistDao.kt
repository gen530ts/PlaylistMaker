package gen.test.android.playlistmaker.data.db.tracksplists

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackPlistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(track: TrackPlistDB)
    @Query("SELECT * FROM track_plist_table WHERE trackid = :id")
    suspend fun getTrackPlistById(id: Int):TrackPlistDB
    @Query("SELECT * FROM track_plist_table")
    suspend fun getAllTracks(): List<TrackPlistDB>
    @Query("DELETE FROM track_plist_table WHERE trackid = :id")
    suspend fun delTrackPlistById(id: Int)
}