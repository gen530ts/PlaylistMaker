package gen.test.android.playlistmaker.domain.player.impl

import gen.test.android.playlistmaker.domain.player.GetTrackRepository
import gen.test.android.playlistmaker.domain.player.GetTrackUseCase
import gen.test.android.playlistmaker.domain.player.models.Track

class GetTrackUseCaseImpl(private val getTrack: GetTrackRepository) : GetTrackUseCase {
    override fun execute(json: String): Track {
        return getTrack.execute(json)
    }
}