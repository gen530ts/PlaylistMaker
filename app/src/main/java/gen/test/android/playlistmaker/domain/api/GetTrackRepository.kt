package gen.test.android.playlistmaker.domain.api

import gen.test.android.playlistmaker.domain.models.Track

interface GetTrackRepository {
    fun execute(json: String): Track
}