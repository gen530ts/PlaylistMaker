package gen.test.android.playlistmaker.data

import com.google.gson.Gson
import gen.test.android.playlistmaker.Track
import gen.test.android.playlistmaker.domain.api.GetTrackUseCase

class GetTrackUseCaseImpl : GetTrackUseCase {
    override fun execute(json: String?): Track {
        return Gson().fromJson(json, Track::class.java)
    }
}