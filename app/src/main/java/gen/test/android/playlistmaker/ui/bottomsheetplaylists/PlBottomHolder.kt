package gen.test.android.playlistmaker.ui.bottomsheetplaylists

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist

class PlBottomHolder  (view: View): RecyclerView.ViewHolder(view) {
    private val img: ImageView = itemView.findViewById(R.id.coverPlIv)
    private val title: TextView = itemView.findViewById(R.id.namePl)
    private val description: TextView = itemView.findViewById(R.id.numberTrack)
    fun bind(plist: Plist) {
        Glide.with(itemView)
            .load(plist.imagePath)
            .fitCenter()
            .placeholder(R.drawable.placeholder_track)
            .transform(RoundedCorners(8))
            .into(img)
        title.text = plist.name
        description.text = plist.tracksNumber
    }
}