package gen.test.android.playlistmaker.domain

import gen.test.android.playlistmaker.domain.api.GetTrack
import gen.test.android.playlistmaker.domain.api.GetTrackUseCase
import gen.test.android.playlistmaker.domain.models.Track

class GetTrackUseCaseImpl(private val getTrack:GetTrack) :GetTrackUseCase{
    override fun execute(json: String): Track {
        return getTrack.execute(json)
    }
}