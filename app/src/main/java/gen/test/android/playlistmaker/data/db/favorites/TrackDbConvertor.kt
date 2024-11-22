package gen.test.android.playlistmaker.data.db.favorites

import gen.test.android.playlistmaker.domain.models.Track

class TrackDbConvertor {
    fun map(track: TrackDB): Track {
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

    fun map(track: Track): TrackDB {
        return TrackDB(
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