package gen.test.android.playlistmaker.data.db

import androidx.room.*

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackDB)
    @Query("DELETE FROM track_table WHERE trackid = :id")
    suspend fun deleteTrack(id: Int)
    @Query("SELECT * FROM track_table ORDER BY create_at")
    suspend fun getTracks(): List<TrackDB>
    @Query("SELECT trackId FROM track_table")
    suspend fun getTrackId(): List<Int>
}

//@Delete(entity = TrackDB::class) DESC