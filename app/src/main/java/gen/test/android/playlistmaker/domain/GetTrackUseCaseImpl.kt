package gen.test.android.playlistmaker.domain

import gen.test.android.playlistmaker.domain.api.GetTrackRepository
import gen.test.android.playlistmaker.domain.api.GetTrackUseCase
import gen.test.android.playlistmaker.domain.models.Track

class GetTrackUseCaseImpl(private val getTrack:GetTrackRepository) :GetTrackUseCase{
    override fun execute(json: String): Track {
        return getTrack.execute(json)
    }
}