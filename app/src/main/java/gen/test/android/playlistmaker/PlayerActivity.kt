package gen.test.android.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
const val ROUNDED_CORNERS_PLAYER=8f
const val KEY_PLAYER_ACTIVITY = "KEY_PLAYER_ACTIVITY"
class PlayerActivity : AppCompatActivity() {

    private fun setBackListener() {
        val back = findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            finish()
        }
    }


    private fun setInfo(rc:Int) {

        val bundle = intent.extras
        val json = bundle!!.getString(KEY_PLAYER_ACTIVITY)
        val track = Gson().fromJson(json, Track::class.java)

     val trackTV = findViewById<TextView>(R.id.trackTV)
     trackTV.text=track.trackName
     val artistTV = findViewById<TextView>(R.id.artistTV)
     artistTV.text=track.artistName
     val timeTv = findViewById<TextView>(R.id.timeTv)
     timeTv.text=Utils.millisToMmSs(track.trackTimeMillis)
     if (track.collectionName.isNullOrEmpty()) {
         val albumGr = findViewById<Group>(R.id.albumGroup)
         albumGr.visibility = View.GONE
     }else{
         val albumTv = findViewById<TextView>(R.id.albumTv)
         albumTv.text=track.collectionName
     }
     if (track.releaseDate.isNullOrEmpty() || ((track.releaseDate.length) < 5)) {
         val yearGr = findViewById<Group>(R.id.yearGroup)
         yearGr.visibility = View.GONE
     }else{
         val yearTv = findViewById<TextView>(R.id.yearTv)
         yearTv.text=track.releaseDate.substring(0, 4)
     }
     val genreTv = findViewById<TextView>(R.id.genreTv)
     genreTv.text=track.primaryGenreName
     val countryTv = findViewById<TextView>(R.id.countryTv)
     countryTv.text=track.country

     val iv = findViewById<ImageView>(R.id.playerIV)
     Glide.with(this)
         .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
         .fitCenter()
         .placeholder(R.drawable.placeholder_player)
         .transform(RoundedCorners(rc))
         .into(iv)
 }

 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_player)
     setInfo(Utils.dpToPx(ROUNDED_CORNERS_PLAYER, this))
     setBackListener()
 }
}