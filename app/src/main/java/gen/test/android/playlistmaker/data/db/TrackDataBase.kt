package gen.test.android.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TrackDB::class])
abstract class TrackDatabase : RoomDatabase(){
    abstract fun trackDao(): TrackDao
}

