package gen.test.android.playlistmaker.data.search.impl

import gen.test.android.playlistmaker.data.search.NetworkClient
import gen.test.android.playlistmaker.data.search.SearchRepository
import gen.test.android.playlistmaker.data.search.model.TrackSearchRequest
import gen.test.android.playlistmaker.data.search.model.TrackSearchResponse
import gen.test.android.playlistmaker.domain.search.model.TrackSearch
import gen.test.android.playlistmaker.utils.Resource

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {
    override fun searchTrack(expression: String): Resource<List<TrackSearch>> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        return when (response.resultCode) {
            200 -> {

                if ((response as TrackSearchResponse).results.isEmpty()) {
                    Resource.NotFound()
                } else {
                    Resource.Success(
                        response.results.map
                        {
                            TrackSearch(
                                it.trackName,
                                it.artistName,
                                it.trackTimeMillis,
                                it.artworkUrl100,
                                it.trackId,
                                it.collectionName,
                                it.releaseDate,
                                it.primaryGenreName,
                                it.country,
                                it.previewUrl
                            )
                        }
                    )
                }
            }
            else -> {
                Resource.ComProblem()
            }
        }
    }

}