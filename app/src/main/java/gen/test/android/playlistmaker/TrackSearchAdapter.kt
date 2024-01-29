package gen.test.android.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackSearchAdapter(private val tracks: List<Track>) : RecyclerView.Adapter<SearchTrackHolder>
    () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTrackHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_track, parent, false)
        return SearchTrackHolder(view)
    }

    override fun onBindViewHolder(holder: SearchTrackHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}