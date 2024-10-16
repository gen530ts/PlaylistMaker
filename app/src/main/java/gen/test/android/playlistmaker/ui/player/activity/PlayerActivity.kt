package gen.test.android.playlistmaker.ui.player.activity


import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.ui.player.model.ModifyUI
import gen.test.android.playlistmaker.ui.player.view_model.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ROUNDED_CORNERS_PLAYER = 8f
const val KEY_PLAYER_ACTIVITY = "KEY_PLAYER_ACTIVITY"

class PlayerActivity : AppCompatActivity() {

    private val viewModel by viewModel<PlayerViewModel>()
    private lateinit var playBtn: ImageButton
    private lateinit var timePlayTV: TextView

    private fun setBackListener() {
        val back = findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setInfo(rc: Int) {

        val bundle = intent.extras
        val json = bundle!!.getString(KEY_PLAYER_ACTIVITY)
        playBtn = findViewById(R.id.playIB)
        timePlayTV = findViewById(R.id.timePlayTV)


        val trackTV = findViewById<TextView>(R.id.trackTV)
        val artistTV = findViewById<TextView>(R.id.artistTV)
        val timeTv = findViewById<TextView>(R.id.timeTv)
        val albumGr = findViewById<Group>(R.id.albumGroup)
        val albumTv = findViewById<TextView>(R.id.albumTv)
        val yearGr = findViewById<Group>(R.id.yearGroup)
        val yearTv = findViewById<TextView>(R.id.yearTv)
        val genreTv = findViewById<TextView>(R.id.genreTv)
        val countryTv = findViewById<TextView>(R.id.countryTv)
        val iv = findViewById<ImageView>(R.id.playerIV)

        viewModel.getTrackLD().observe(this) {
            if (it.previewUrl != null) {
               viewModel.preparePlayer(it.previewUrl)
                playBtn.setOnClickListener { viewModel.playbackControl() }
            }
            trackTV.text = it.trackName
            artistTV.text = it.artistName
            
            timeTv.text = Utils.millisToMmSs(it.trackTimeMillis)
            if (it.collectionName.isNullOrEmpty()) {

                albumGr.isVisible=false
            } else {
                albumTv.text = it.collectionName
            }
            if (it.releaseDate.isNullOrEmpty() || ((it.releaseDate.length) < 5)) {

                yearGr.isVisible=false
            } else {
                yearTv.text = it.releaseDate.substring(0, 4)
            }
            genreTv.text = it.primaryGenreName
            countryTv.text = it.country
            Glide.with(this)
                .load(it.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .fitCenter()
                .placeholder(R.drawable.placeholder_player)
                .transform(RoundedCorners(rc))
                .into(iv)
        }
        viewModel.getModUI().observe(this) {
            when (it) {
                is ModifyUI.TimePlayTV -> {
                    
                    timePlayTV.text = it.txt
                
                }
                is ModifyUI.PlayBtn -> {
                    playBtn.isEnabled = it.isEn
                }

                is ModifyUI.PlayBtnImagePlay -> {
                    if (it.isEn) playBtn.setImageResource(R.drawable.play_btn)
                    else playBtn.setImageResource(R.drawable.pause_btn)
                }
            }
        }
        if (json != null) {
            viewModel.prepare(json)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setInfo(Utils.dpToPx(ROUNDED_CORNERS_PLAYER, this))
        setBackListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }


}