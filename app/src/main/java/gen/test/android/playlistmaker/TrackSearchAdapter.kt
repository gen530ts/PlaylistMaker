package gen.test.android.playlistmaker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackSearchAdapter : RecyclerView.Adapter<SearchTrackHolder>
    () {
    var tracks= ArrayList<Track>()
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
//private val tracks: List<Track>


