package gen.test.android.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import gen.test.android.playlistmaker.data.db.favorites.TrackDB
import gen.test.android.playlistmaker.data.db.favorites.TrackDao
import gen.test.android.playlistmaker.data.db.playlists.PlistDB
import gen.test.android.playlistmaker.data.db.playlists.PlistDao
import gen.test.android.playlistmaker.data.db.tracksplists.TrackPlistDB
import gen.test.android.playlistmaker.data.db.tracksplists.TrackPlistDao

@Database(version = 1, entities = [TrackDB::class,PlistDB::class,TrackPlistDB::class])
abstract class TrackDatabase : RoomDatabase(){
    abstract fun trackDao(): TrackDao
    abstract fun plistDao(): PlistDao
    abstract fun trackPlistDao(): TrackPlistDao
}

