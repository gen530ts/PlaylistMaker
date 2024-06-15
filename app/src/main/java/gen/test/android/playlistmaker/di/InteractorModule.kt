package gen.test.android.playlistmaker.di

import gen.test.android.playlistmaker.domain.player.GetTrackUseCase
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
import org.koin.dsl.module

val interactorModule = module {

    single<GetTrackUseCase> {
        GetTrackUseCaseImpl(get())
    }

    factory <PlayerInteractor> {
        PlayerInteractorImpl(get())
    }

    single<HistoryInteractor> {
        HistoryInteractorImpl( get())
    }

    single<TrackSearchInteractor>{
        TrackSearchInteractorImpl(get())
    }

    single<ThemeInteractor>{
        ThemeInteractorImpl(get())
    }

    single<SharingInteractor>{
        SharingInteractorImpl(get(),get())
    }
}