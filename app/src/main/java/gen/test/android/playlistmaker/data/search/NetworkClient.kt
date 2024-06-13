package gen.test.android.playlistmaker.data.search

import gen.test.android.playlistmaker.data.search.model.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}