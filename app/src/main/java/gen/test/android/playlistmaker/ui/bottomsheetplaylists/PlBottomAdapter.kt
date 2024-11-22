package gen.test.android.playlistmaker.ui.bottomsheetplaylists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.models.Plist

class PlBottomAdapter(
    private val plist: List<Plist>,
    private val plListener: PlClickListener
) :
    RecyclerView.Adapter<PlBottomHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType:
        Int
    ): PlBottomHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
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
        holder.bind(plist[position])
        holder.itemView.setOnClickListener { plListener.onPlClick(plist[position]) }
    }

    fun interface PlClickListener {
        fun onPlClick(pl:Plist)
    }
}