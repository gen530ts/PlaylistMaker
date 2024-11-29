package gen.test.android.playlistmaker.di

import gen.test.android.playlistmaker.data.db.favorites.FavoriteRepositoryImpl
import gen.test.android.playlistmaker.data.db.favorites.TrackDbConvertor
import gen.test.android.playlistmaker.data.db.playlists.ImageHandler
import gen.test.android.playlistmaker.data.db.playlists.PlistDbConvertor
import gen.test.android.playlistmaker.data.db.playlists.PlistRepositoryImpl
import gen.test.android.playlistmaker.data.db.tracksplists.TrackPlDbConvertor
import gen.test.android.playlistmaker.data.player.impl.GetTrackRepositoryImpl
import gen.test.android.playlistmaker.data.search.HistoryRepository
import gen.test.android.playlistmaker.data.search.SearchRepository
import gen.test.android.playlistmaker.data.search.impl.HistoryRepositoryImpl
import gen.test.android.playlistmaker.data.search.impl.SearchRepositoryImpl
import gen.test.android.playlistmaker.data.settings.impl.ThemeRepositoryImpl
import gen.test.android.playlistmaker.data.sharing.SharingRepository
import gen.test.android.playlistmaker.data.sharing.impl.SharingRepositoryImpl
import gen.test.android.playlistmaker.domain.db.FavoriteRepository
import gen.test.android.playlistmaker.domain.db.PlistRepository
import gen.test.android.playlistmaker.domain.player.GetTrackRepository
import gen.test.android.playlistmaker.domain.settings.ThemeRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single<GetTrackRepository> {
        GetTrackRepositoryImpl()
    }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(),get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get(),get())
    }

    single<ThemeRepository> {
        ThemeRepositoryImpl(androidApplication())
    }

    single<SharingRepository> {
        SharingRepositoryImpl(androidContext())
    }

    factory { TrackDbConvertor() }

    factory { TrackPlDbConvertor() }

    factory { PlistDbConvertor() }

    factory { ImageHandler(androidContext()) }

    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }

    single<PlistRepository> { PlistRepositoryImpl(get(), get(), get(), get()) }

}

