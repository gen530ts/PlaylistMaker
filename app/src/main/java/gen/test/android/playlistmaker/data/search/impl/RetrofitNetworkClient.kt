package gen.test.android.playlistmaker.data.search.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import gen.test.android.playlistmaker.ItunesAppleApi
import gen.test.android.playlistmaker.data.search.NetworkClient
import gen.test.android.playlistmaker.data.search.model.Response
import gen.test.android.playlistmaker.data.search.model.TrackSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient(private val context: Context) : NetworkClient {
    private var baseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val musicService = retrofit.create(ItunesAppleApi::class.java)
    override fun doRequest(dto: Any): Response {
        if(!isConnected())
        {
            return Response().apply{
                resultCode = -1
            }
        }
        if(dto !is TrackSearchRequest)
        {
            return Response().apply{
                resultCode = 400
            }
        }
        val response = musicService.search(dto.expression).execute()
        val body = response.body()
        return body?.apply{
            resultCode = response.code()
        } ?: Response().apply{
                resultCode = response.code()
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