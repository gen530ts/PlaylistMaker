package gen.test.android.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import androidx.room.Room
import com.google.gson.Gson
import gen.test.android.playlistmaker.SHARED_PREFERENCES
import gen.test.android.playlistmaker.data.db.TrackDatabase
import gen.test.android.playlistmaker.data.player.impl.MediaPlayerWrapperImpl
import gen.test.android.playlistmaker.data.search.HistoryManager
import gen.test.android.playlistmaker.data.search.ItunesAppleApi
import gen.test.android.playlistmaker.data.search.NetworkClient
import gen.test.android.playlistmaker.data.search.impl.HistoryManagerImpl
import gen.test.android.playlistmaker.data.search.impl.RetrofitNetworkClient
import gen.test.android.playlistmaker.data.sharing.ExternalNavigator
import gen.test.android.playlistmaker.data.sharing.impl.ExternalNavigatorImpl
import gen.test.android.playlistmaker.domain.player.MediaPlayerWrapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<ItunesAppleApi>{
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesAppleApi::class.java)
    }

    single {
        androidContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    factory { MediaPlayer() }


    single<HistoryManager>{
        HistoryManagerImpl(get(), get())
    }

    single<NetworkClient>{
        RetrofitNetworkClient(androidContext(), get())
    }

    factory<MediaPlayerWrapper>{
        MediaPlayerWrapperImpl(get())
    }

    single<ExternalNavigator>{
        ExternalNavigatorImpl(androidContext())
    }

    single {
        Room.databaseBuilder(androidContext(),
            TrackDatabase::class.java, "database.db")
            .build()
    }



}