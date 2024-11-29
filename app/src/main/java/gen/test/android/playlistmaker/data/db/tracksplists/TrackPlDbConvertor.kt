package gen.test.android.playlistmaker.data.db.tracksplists

import gen.test.android.playlistmaker.domain.models.Track

class TrackPlDbConvertor {
    fun map(track: TrackPlistDB): Track {
        return Track(
            track.trackName,
            track.artistName,
            track.trackTime.toInt(),
            track.artworkUrl100,
            track.trackId,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl
        )
    }

    fun map(track: Track): TrackPlistDB {
        return TrackPlistDB(
            track.trackId,
            track.artworkUrl100,
            track.trackName,
            track.artistName,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.trackTimeMillis.toString(),
            track.previewUrl,
            create_at = System.currentTimeMillis()
        )
    }
}