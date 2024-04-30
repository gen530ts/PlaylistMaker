package gen.test.android.playlistmaker

import gen.test.android.playlistmaker.data.GetTrackImpl
import gen.test.android.playlistmaker.data.PlayerImpl
import gen.test.android.playlistmaker.domain.GetTrackUseCaseImpl
import gen.test.android.playlistmaker.domain.PlayerInteractorImpl
import gen.test.android.playlistmaker.domain.api.GetTrackUseCase
import gen.test.android.playlistmaker.domain.api.PlayerInteractor

object Creator {
    val getTrack: GetTrackUseCase = GetTrackUseCaseImpl(GetTrackImpl())
    val getPlayerInteractor: PlayerInteractor = PlayerInteractorImpl(PlayerImpl())
}