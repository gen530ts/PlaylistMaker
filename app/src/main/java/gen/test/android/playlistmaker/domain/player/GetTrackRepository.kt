package gen.test.android.playlistmaker.domain.player

import gen.test.android.playlistmaker.domain.player.models.Track

interface GetTrackRepository {
    fun execute(json: String): Track
}