package gen.test.android.playlistmaker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class SearchTrackHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val  songView: TextView
    private val creatorView: TextView
    private val lengthView: TextView
    private val coverImgView:ImageView

    init {
        songView = itemView.findViewById(R.id.songTextView)
        creatorView = itemView.findViewById(R.id.creatorTextView)
        lengthView = itemView.findViewById(R.id.lengthTextView)
        coverImgView=itemView.findViewById(R.id.coverImageView)
    }

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