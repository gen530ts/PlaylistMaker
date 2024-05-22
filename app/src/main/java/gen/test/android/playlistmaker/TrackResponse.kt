package gen.test.android.playlistmaker

import gen.test.android.playlistmaker.domain.player.models.Track

class TrackResponse(
    val resultCount: Int,
    val results: List<Track>)