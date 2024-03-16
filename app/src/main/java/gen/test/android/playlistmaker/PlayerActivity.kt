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
        val arrVal = arrayOf(track.trackName, track.artistName, track.trackTimeMillis, track
            .collectionName, track.releaseDate, track.primaryGenreName, track.country)
        val arrTv =
            arrayOf(R.id.trackTV, R.id.artistTV, R.id.timeTv, R.id.albumTv, R.id.yearTv, R.id
                .genreTv, R.id.countryTv)
        for (i in arrVal.indices) {
            if (arrVal[i] != null) {
                val tv = findViewById<TextView>(arrTv[i])
                when (arrTv[i]) {
                    R.id.timeTv -> tv.text = Utils.millisToMmSs(arrVal[i] as Int)
                    R.id.yearTv -> tv.text = (arrVal[i] as String).substring(0, 4)
                    else -> tv.text = arrVal[i].toString()
                }
            } else if (arrTv[i] == R.id.albumTv) {
                val albumGr = findViewById<Group>(R.id.albumGroup)
                albumGr.visibility = View.GONE
            }
        }
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