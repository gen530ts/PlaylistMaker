package gen.test.android.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import gen.test.android.playlistmaker.domain.player.models.Track

class SearchTrackHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_track, parent, false)) {
    private val  songView: TextView = itemView.findViewById(R.id.songTextView)
    private val creatorView: TextView = itemView.findViewById(R.id.creatorTextView)
    private val lengthView: TextView = itemView.findViewById(R.id.lengthTextView)
    private val coverImgView:ImageView = itemView.findViewById(R.id.coverImageView)
    private val rt=Utils.dpToPx(2f,parent.context)



    fun bind(model: Track) {
        songView.text = model.trackName
        creatorView.text = model.artistName
        lengthView.text= Utils.millisToMmSs((model.trackTimeMillis))
        //Log.d("dptopx", "dptopx = $rt;model.trackName = ${model.trackName}")
        Glide.with(itemView)
            .load(model.artworkUrl100)
            .fitCenter()
            .placeholder(R.drawable.placeholder_track)
            .transform(RoundedCorners(rt))
            .into(coverImgView)
    }
}