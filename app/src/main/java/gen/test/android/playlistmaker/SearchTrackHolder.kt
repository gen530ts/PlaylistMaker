package gen.test.android.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class SearchTrackHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_track, parent, false)) {
    private val  songView: TextView = itemView.findViewById(R.id.songTextView)
    private val creatorView: TextView = itemView.findViewById(R.id.creatorTextView)
    private val lengthView: TextView = itemView.findViewById(R.id.lengthTextView)
    private val coverImgView:ImageView = itemView.findViewById(R.id.coverImageView)



    fun bind(model: Track) {
        songView.text = model.trackName
        creatorView.text = model.artistName
        lengthView.text=model.trackTime
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .fitCenter()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(2))
            .into(coverImgView)
    }
}