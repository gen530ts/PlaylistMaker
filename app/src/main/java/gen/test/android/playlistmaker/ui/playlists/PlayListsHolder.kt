package gen.test.android.playlistmaker.ui.playlists

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist

class PlayListsHolder (view: View): RecyclerView.ViewHolder(view) {
    private val img: ImageView = itemView.findViewById(R.id.img)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)
    fun bind(plist: Plist) {

        if(plist.imageUri!=null){
            img.setImageURI(plist.imageUri)
        }
        title.text = plist.name
        description.text = plist.tracksNumber
    }
}