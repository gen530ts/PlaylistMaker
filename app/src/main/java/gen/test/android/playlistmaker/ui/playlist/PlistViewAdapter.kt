package gen.test.android.playlistmaker.ui.playlist

import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.ui.search.activity.SearchTrackHolder
import gen.test.android.playlistmaker.ui.search.activity.TrackSearchAdapter

class PlistViewAdapter(private val tlcl:TrackLongClickListener, tcl: TrackClickListener) :
    TrackSearchAdapter
    (tcl){
    override fun onBindViewHolder(holder: SearchTrackHolder, position: Int) {

        super.onBindViewHolder(holder, position)
        holder.itemView.setOnLongClickListener {
            tlcl.onTrackLongClick(tracks[position])
            true
        }
    }
    fun interface TrackLongClickListener {
        fun onTrackLongClick(location: Track)
    }
}

