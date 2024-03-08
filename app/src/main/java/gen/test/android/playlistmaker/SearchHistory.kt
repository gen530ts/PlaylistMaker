package gen.test.android.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson

const val SEARCH_HISTORY_KEY = "search_history_key"

class SearchHistory(private val shPf: SharedPreferences) {

    fun read(): ArrayList<Track> {
        val json = shPf.getString(SEARCH_HISTORY_KEY, null) ?: return arrayListOf()
        return Gson().fromJson(json, Array<Track>::class.java).toMutableList() as ArrayList<Track>
    }

    fun clearHistory() {
        shPf.edit()
            .putString(SEARCH_HISTORY_KEY, "[]")
            .apply()
    }

    fun add(track: Track) {
        val arr = read()
        if (arr.isNotEmpty()) {
            val iterator = arr.iterator()
            var cnt = 0
            for (i in iterator) {
                if ((i.trackId == track.trackId) || (cnt > 8)) {
                    iterator.remove()
                }
                cnt++
            }
        }
        arr.add(0, track)

        write(arr)
    }

    private fun write(track: ArrayList<Track>) {
        val json = Gson().toJson(track)
        shPf.edit()
            .putString(SEARCH_HISTORY_KEY, json)
            .apply()
    }
}