package gen.test.android.playlistmaker.data

import com.google.gson.Gson
import gen.test.android.playlistmaker.domain.api.GetTrack

import gen.test.android.playlistmaker.domain.models.Track

class GetTrackImpl : GetTrack {
    override fun execute(json: String): Track {
        return Gson().fromJson(json, Track::class.java)
    }
}