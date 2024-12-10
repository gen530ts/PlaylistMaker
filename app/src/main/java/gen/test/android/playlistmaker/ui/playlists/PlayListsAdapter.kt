package gen.test.android.playlistmaker.ui.playlists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist
import java.io.File

class PlayListsAdapter (
    private val plist: List<Plist>,
    private val filePath: File
):
    RecyclerView.Adapter<PlayListsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): PlayListsHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.play_lists_img,
                parent, false)
        return PlayListsHolder(view)
    }
    override fun getItemCount(): Int {
        return plist.size
    }
    override fun onBindViewHolder(holder: PlayListsHolder, position:
    Int) {
        holder.bind(plist[position],filePath)
    }
}