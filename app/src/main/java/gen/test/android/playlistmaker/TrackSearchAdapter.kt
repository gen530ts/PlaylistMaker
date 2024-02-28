package gen.test.android.playlistmaker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackSearchAdapter(private val trackListener: TrackClickListener) : RecyclerView
.Adapter<SearchTrackHolder>
    () {
    fun setItems(items: ArrayList<Track>){
       tracks=items
    }
    private var tracks= ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTrackHolder {

        return SearchTrackHolder(parent)
    }

    override fun onBindViewHolder(holder: SearchTrackHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { trackListener.onTrackClick(tracks[position]) }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
    fun interface TrackClickListener {
        fun onTrackClick(location: Track)
    }
}
//private val tracks: List<Track>


