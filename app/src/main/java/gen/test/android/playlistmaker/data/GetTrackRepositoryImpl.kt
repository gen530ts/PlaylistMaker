package gen.test.android.playlistmaker.data

import com.google.gson.Gson
import gen.test.android.playlistmaker.data.models.TrackDto
import gen.test.android.playlistmaker.domain.api.GetTrackRepository
import gen.test.android.playlistmaker.domain.models.Track

class GetTrackRepositoryImpl : GetTrackRepository {
    override fun execute(json: String): Track {
        val trackDto = Gson().fromJson(json, TrackDto::class.java)
        return Track(
            trackName = trackDto.trackName,
            artistName = trackDto.artistName,
            trackTimeMillis = trackDto.trackTimeMillis,
            artworkUrl100 = trackDto.artworkUrl100,
            trackId = trackDto.trackId,
            collectionName = trackDto.collectionName,
            releaseDate = trackDto.releaseDate,
            primaryGenreName = trackDto.primaryGenreName,
            country = trackDto.country,
            previewUrl = trackDto.previewUrl
        )
    }
}