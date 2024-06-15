package gen.test.android.playlistmaker.creator

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import com.google.gson.Gson
import gen.test.android.playlistmaker.data.player.impl.GetTrackRepositoryImpl
import gen.test.android.playlistmaker.data.player.impl.MediaPlayerWrapperImpl
import gen.test.android.playlistmaker.data.search.HistoryRepository
import gen.test.android.playlistmaker.data.search.ItunesAppleApi
import gen.test.android.playlistmaker.data.search.SearchRepository
import gen.test.android.playlistmaker.data.search.impl.HistoryManagerImpl
import gen.test.android.playlistmaker.data.search.impl.HistoryRepositoryImpl
import gen.test.android.playlistmaker.data.search.impl.RetrofitNetworkClient
import gen.test.android.playlistmaker.data.search.impl.SearchRepositoryImpl
import gen.test.android.playlistmaker.data.settings.impl.ThemeRepositoryImpl
import gen.test.android.playlistmaker.data.sharing.ExternalNavigator
import gen.test.android.playlistmaker.data.sharing.SharingRepository
import gen.test.android.playlistmaker.data.sharing.impl.ExternalNavigatorImpl
import gen.test.android.playlistmaker.data.sharing.impl.SharingRepositoryImpl
import gen.test.android.playlistmaker.domain.player.GetTrackUseCase
import gen.test.android.playlistmaker.domain.player.MediaPlayerWrapper
import gen.test.android.playlistmaker.domain.player.PlayerInteractor
import gen.test.android.playlistmaker.domain.player.impl.GetTrackUseCaseImpl
import gen.test.android.playlistmaker.domain.player.impl.PlayerInteractorImpl
import gen.test.android.playlistmaker.domain.search.HistoryInteractor
import gen.test.android.playlistmaker.domain.search.TrackSearchInteractor
import gen.test.android.playlistmaker.domain.search.impl.HistoryInteractorImpl
import gen.test.android.playlistmaker.domain.search.impl.TrackSearchInteractorImpl
import gen.test.android.playlistmaker.domain.settings.ThemeInteractor
import gen.test.android.playlistmaker.domain.settings.impl.ThemeInteractorImpl
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor
import gen.test.android.playlistmaker.domain.sharing.impl.SharingInteractorImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {
    val getTrack: GetTrackUseCase = GetTrackUseCaseImpl(GetTrackRepositoryImpl())

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(providePlayer())
    }

    private fun providePlayer(): MediaPlayerWrapper {
        return MediaPlayerWrapperImpl(MediaPlayer())
    }

    private fun provideExternalNavigator(context: Context): ExternalNavigator {
        return ExternalNavigatorImpl(context)
    }

    private fun provideSharingRepository(context: Context): SharingRepository {
        return SharingRepositoryImpl(context)
    }

    fun provideSharingInteractor(
        context: Context,
       
       
    ): SharingInteractor {
        return SharingInteractorImpl(
            provideExternalNavigator(context),
            provideSharingRepository(context)
        )
    }
    fun provideThemeInteractor(application:Application):ThemeInteractor{
        return ThemeInteractorImpl(ThemeRepositoryImpl(application))
    }

    private fun getSearchRepository(context: Context): SearchRepository {
        val baseUrl = "https://itunes.apple.com"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val musicService = retrofit.create(ItunesAppleApi::class.java)
        return SearchRepositoryImpl(RetrofitNetworkClient(context,musicService))
    }

    fun provideSearchInteractor(context: Context): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getSearchRepository(context))
    }

    private fun getHistoryRepository(shPref:SharedPreferences): HistoryRepository {
        return HistoryRepositoryImpl(HistoryManagerImpl(shPref, Gson()))
    }

    fun provideHistoryInteractor(shPref:SharedPreferences): HistoryInteractor {
        return HistoryInteractorImpl(getHistoryRepository(shPref))
    }
}
