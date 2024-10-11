package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.data.search.model.Response

interface NetworkClient {
   suspend fun doRequest(dto: Any): Response
}