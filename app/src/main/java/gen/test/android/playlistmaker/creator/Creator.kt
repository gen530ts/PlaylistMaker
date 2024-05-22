package gen.test.android.playlistmaker.creator

import android.app.Application
import android.content.Context
import gen.test.android.playlistmaker.data.player.impl.GetTrackRepositoryImpl
import gen.test.android.playlistmaker.data.player.impl.MediaPlayerWrapperImpl
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
import gen.test.android.playlistmaker.domain.settings.ThemeInteractor
import gen.test.android.playlistmaker.domain.settings.impl.ThemeInteractorImpl
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor
import gen.test.android.playlistmaker.domain.sharing.impl.SharingInteractorImpl

object Creator {
    val getTrack: GetTrackUseCase = GetTrackUseCaseImpl(GetTrackRepositoryImpl())

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(providePlayer())
    }

    private fun providePlayer(): MediaPlayerWrapper {
        return MediaPlayerWrapperImpl()
    }

    private fun provideExternalNavigator(context: Context): ExternalNavigator {
        return ExternalNavigatorImpl(context)
    }

    private fun provideSharingRepository(context: Context): SharingRepository {
        return SharingRepositoryImpl(context)
    }

    fun provideSharingInteractor(
        context: Context,
       // externalNavigator: ExternalNavigator,
       // sharingRepository: SharingRepository
    ): SharingInteractor {
        return SharingInteractorImpl(
            provideExternalNavigator(context),
            provideSharingRepository(context)
        )
    }
    fun provideThemeInteractor(application:Application):ThemeInteractor{
        return ThemeInteractorImpl(ThemeRepositoryImpl(application))
    }


}
