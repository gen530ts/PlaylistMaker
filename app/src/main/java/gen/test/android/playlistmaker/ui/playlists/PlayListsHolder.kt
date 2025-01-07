package gen.test.android.playlistmaker.ui.playlists

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist
import java.io.File

class PlayListsHolder (view: View): RecyclerView.ViewHolder(view) {
    private val img: ImageView = itemView.findViewById(R.id.img)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)
    fun bind(plist: Plist,filePath: File) {
        val uriStr=plist.imagePath
        if(uriStr.isNotEmpty()){
            val fileName=plist.name+plist.description+".jpg"
            val file = File(filePath, fileName)
            img.setImageURI(file.toUri())

        }

        title.text = plist.name
        description.text = plist.tracksNumber
    }
}