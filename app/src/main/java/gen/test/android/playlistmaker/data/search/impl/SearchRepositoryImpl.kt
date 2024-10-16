package gen.test.android.playlistmaker.data.search.impl

import android.util.Log
import gen.test.android.playlistmaker.data.search.NetworkClient
import gen.test.android.playlistmaker.data.search.SearchRepository
import gen.test.android.playlistmaker.data.search.model.TrackSearchRequest
import gen.test.android.playlistmaker.data.search.model.TrackSearchResponse
import gen.test.android.playlistmaker.domain.search.model.TrackSearch
import gen.test.android.playlistmaker.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(private val networkClient: NetworkClient) : SearchRepository {
    override fun searchTrack(expression: String): Flow<Resource<List<TrackSearch>>> = flow  {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        Log.d("mytag","SearchRepositoryImpl.response.resultCode=${response.resultCode}")
        when (response.resultCode) {
            200 -> {

                if ((response as TrackSearchResponse).results.isEmpty()) {
                   emit(Resource.NotFound())
                } else {
                    emit(Resource.Success(
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
                    ))
                }
            }
            else -> {
                emit(Resource.ComProblem())
            }
        }
    }

}


