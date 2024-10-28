package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchTrack(expression: String): Flow<Resource<List<Track>>>
}