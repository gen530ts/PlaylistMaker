package gen.test.android.playlistmaker.ui.bottomsheetplaylists

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist
import java.io.File

class PlBottomHolder  (view: View): RecyclerView.ViewHolder(view) {
    private val img: ImageView = itemView.findViewById(R.id.coverPlIv)
    private val title: TextView = itemView.findViewById(R.id.namePl)
    private val description: TextView = itemView.findViewById(R.id.numberTrack)
    fun bind(plist: Plist,filePath: File) {
        if(plist.imagePath.isNotEmpty()){
            val fileName=plist.name+plist.description+".jpg"
            val file = File(filePath, fileName)

            img.setImageURI(file.toUri())
        }
        else img.setImageResource(R.drawable.placeholder_track)

        title.text = plist.name
        description.text = plist.tracksNumber
    }
}