package gen.test.android.playlistmaker.data.search.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import gen.test.android.playlistmaker.data.search.ItunesAppleApi
import gen.test.android.playlistmaker.data.search.NetworkClient
import gen.test.android.playlistmaker.data.search.model.Response
import gen.test.android.playlistmaker.data.search.model.TrackSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(private val context: Context,private val musicService:ItunesAppleApi) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if(!isConnected())
        {
            return Response().apply{
                resultCode = -1
            }
        }
        if(dto !is TrackSearchRequest) {
            return Response().apply{
                resultCode = 400
            }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = musicService.search(dto.expression)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }

    }
    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if(capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->return true
            }
        }
        return false
    }
}