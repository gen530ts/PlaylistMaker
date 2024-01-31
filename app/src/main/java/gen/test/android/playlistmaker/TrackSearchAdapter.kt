package gen.test.android.playlistmaker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackSearchAdapter(private val tracks: List<Track>) : RecyclerView.Adapter<SearchTrackHolder>
    () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTrackHolder {

        return SearchTrackHolder(parent)
    }

    override fun onBindViewHolder(holder: SearchTrackHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}


