package gen.test.android.playlistmaker.ui.search.activity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.domain.models.Track

class TrackSearchAdapter(private val trackListener: TrackClickListener) : RecyclerView
.Adapter<SearchTrackHolder>
    () {
    fun setItems(items: ArrayList<Track>){
       tracks=items
    }
    fun clearItems(){
        tracks.clear()
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



