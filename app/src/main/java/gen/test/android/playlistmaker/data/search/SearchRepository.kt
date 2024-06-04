package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.domain.search.model.TrackSearch
import gen.test.android.playlistmaker.utils.Resource

interface SearchRepository {
    fun searchTrack(expression: String): Resource<List<TrackSearch>>
}