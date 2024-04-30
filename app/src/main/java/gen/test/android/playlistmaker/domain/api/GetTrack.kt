package gen.test.android.playlistmaker.domain.api

import gen.test.android.playlistmaker.domain.models.Track

interface GetTrack {
    fun execute(json: String): Track
}