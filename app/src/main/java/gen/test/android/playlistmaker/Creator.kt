package gen.test.android.playlistmaker

import gen.test.android.playlistmaker.data.GetTrackRepositoryImpl
import gen.test.android.playlistmaker.data.MediaPlayerWrapperImpl
import gen.test.android.playlistmaker.domain.GetTrackUseCaseImpl
import gen.test.android.playlistmaker.domain.PlayerInteractorImpl
import gen.test.android.playlistmaker.domain.api.GetTrackUseCase
import gen.test.android.playlistmaker.domain.api.MediaPlayerWrapper
import gen.test.android.playlistmaker.domain.api.PlayerInteractor

object Creator {
    val getTrack: GetTrackUseCase = GetTrackUseCaseImpl(GetTrackRepositoryImpl())

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(providePlayer())
    }

    private fun providePlayer(): MediaPlayerWrapper {
        return MediaPlayerWrapperImpl()
    }


}
