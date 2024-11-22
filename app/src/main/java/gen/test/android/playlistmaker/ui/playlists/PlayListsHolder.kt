package gen.test.android.playlistmaker.ui.playlists

import android.util.Log
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
          //  img.setImageURI(uriStr.toUri())
            Glide.with(itemView)
                .load(uriStr)
                 .placeholder(R.drawable.play_list_default)
                .centerCrop()
                .transform( RoundedCorners(18))
                .into(img)
         /*   */
/*            Glide.with(itemView)
                .load(model.artworkUrl100)
                .fitCenter()
                .placeholder(R.drawable.placeholder_track)
                .transform(RoundedCorners(rt))
                .into(coverImgView)*/
        }
        Log.d("mytag", "uri=$uriStr")
     //   if(uri!=null){.toUri()

            //img.use{}
            //setImageURI(strUri.toUri())
      //  }
        title.text = plist.name
        description.text = plist.tracksNumber
    }
}