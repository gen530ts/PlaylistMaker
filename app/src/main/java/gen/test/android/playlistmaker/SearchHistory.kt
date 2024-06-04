package gen.test.android.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import gen.test.android.playlistmaker.domain.search.model.TrackSearch

const val SEARCH_HISTORY_KEY = "search_history_key"

class SearchHistory(private val shPf: SharedPreferences) {

    fun read(): ArrayList<TrackSearch> {
        val json = shPf.getString(SEARCH_HISTORY_KEY, null) ?: return arrayListOf()
        return Gson().fromJson(json, Array<TrackSearch>::class.java).toMutableList() as ArrayList<TrackSearch>
    }

    fun clearHistory() {
        shPf.edit()
            .putString(SEARCH_HISTORY_KEY, "[]")
            .apply()
    }

    fun add(track: TrackSearch) {
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

    private fun write(track: ArrayList<TrackSearch>) {
        val json = Gson().toJson(track)
        shPf.edit()
            .putString(SEARCH_HISTORY_KEY, json)
            .apply()
    }
}