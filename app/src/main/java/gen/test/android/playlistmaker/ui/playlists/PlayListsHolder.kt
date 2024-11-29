package gen.test.android.playlistmaker.ui.playlists

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist

class PlayListsHolder (view: View): RecyclerView.ViewHolder(view) {
    private val img: ImageView = itemView.findViewById(R.id.img)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)
    fun bind(plist: Plist) {
        val uriStr=plist.imagePath
        if(uriStr.isNotEmpty()){
            Glide.with(itemView)
                .load(uriStr)
                 .placeholder(R.drawable.play_list_default)
                .fitCenter()
                .transform( RoundedCorners(8))
                .into(img)
        }

        title.text = plist.name
        description.text = plist.tracksNumber
    }
}