package gen.test.android.playlistmaker.domain.search

import gen.test.android.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackSearchInteractor {
    fun searchTrack(expression: String): Flow<Pair<List<Track>?, Boolean>>

}

