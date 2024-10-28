package gen.test.android.playlistmaker.data.player.impl

import com.google.gson.Gson
import gen.test.android.playlistmaker.data.player.models.TrackDto
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.domain.player.GetTrackRepository

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
            previewUrl = trackDto.previewUrl,
            isFavorite = trackDto.isFavorite
        )
    }
}