package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.data.search.model.TrackSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesAppleApi {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): TrackSearchResponse
}