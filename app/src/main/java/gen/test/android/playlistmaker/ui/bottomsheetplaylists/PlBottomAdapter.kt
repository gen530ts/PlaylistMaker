package gen.test.android.playlistmaker.ui.bottomsheetplaylists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist
import java.io.File

class PlBottomAdapter(
    private val plListener: PlClickListener,
    private val filePath: File
) : RecyclerView.Adapter<PlBottomHolder>() {

    private var plist = listOf<Plist>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType:
        Int
    ): PlBottomHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.play_lists_bottom,
            parent, false
        )
        return PlBottomHolder(view)
    }

    override fun getItemCount(): Int {
        return plist.size
    }

    override fun onBindViewHolder(
        holder: PlBottomHolder, position:
        Int
    ) {
        holder.bind(plist[position],filePath)
        holder.itemView.setOnClickListener { plListener.onPlClick(plist[position]) }
    }

    fun setData(data: List<Plist>) {
        plist=data
    }

    fun interface PlClickListener {
        fun onPlClick(pl: Plist)
    }
}