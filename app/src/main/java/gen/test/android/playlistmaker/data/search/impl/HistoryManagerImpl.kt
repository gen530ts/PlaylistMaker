package gen.test.android.playlistmaker.data.search.impl

import android.content.SharedPreferences
import com.google.gson.Gson
//import gen.test.android.playlistmaker.SEARCH_HISTORY_KEY
import gen.test.android.playlistmaker.data.search.HistoryManager
import gen.test.android.playlistmaker.data.search.model.TrackSearchDto

const val SEARCH_HISTORY_KEY = "search_history_key"

class HistoryManagerImpl(private val shPf: SharedPreferences): HistoryManager {
    override fun add(track: TrackSearchDto) {
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

    override fun read(): ArrayList<TrackSearchDto> {
        val json = shPf.getString(SEARCH_HISTORY_KEY, null) ?: return arrayListOf()
        return Gson().fromJson(json, Array<TrackSearchDto>::class.java).toMutableList() as
                ArrayList<TrackSearchDto>
    }

    override fun clear() {
        shPf.edit()
            .putString(SEARCH_HISTORY_KEY, "[]")
            .apply()
    }
    private fun write(track: ArrayList<TrackSearchDto>) {
        val json = Gson().toJson(track)
        shPf.edit()
            .putString(SEARCH_HISTORY_KEY, json)
            .apply()
    }
}