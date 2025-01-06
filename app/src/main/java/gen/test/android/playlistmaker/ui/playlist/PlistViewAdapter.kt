package gen.test.android.playlistmaker.ui.playlist

import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.ui.search.activity.SearchTrackHolder
import gen.test.android.playlistmaker.ui.search.activity.TrackSearchAdapter

class PlistViewAdapter(private val tlcl:TrackLongClickListener,private val tcl: TrackClickListener) :
    TrackSearchAdapter
    (tcl){
    override fun onBindViewHolder(holder: SearchTrackHolder, position: Int) {
/*        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { trackListener.onTrackClick(tracks[position]) }*/
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

/*private val callback = object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
        if ((uriCover != null) || (binding.enterPlName.text!!.isNotEmpty()) || binding.enterPlDescr
                .text!!.isNotEmpty()*/

//tlcl.onTrackLongClick(tracks[position])