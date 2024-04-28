package gen.test.android.playlistmaker.domain.api

import gen.test.android.playlistmaker.Track

interface GetTrackUseCase {
    fun execute(json: String?): Track
}