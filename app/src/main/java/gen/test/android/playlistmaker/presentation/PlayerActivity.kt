package gen.test.android.playlistmaker.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import gen.test.android.playlistmaker.Creator
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.domain.models.PlayerState

private const val ROUNDED_CORNERS_PLAYER = 8f
const val KEY_PLAYER_ACTIVITY = "KEY_PLAYER_ACTIVITY"

class PlayerActivity : AppCompatActivity() {

    companion object {
        private const val UPDATE_UI = 300L
    }

    private val playerInteractor = Creator.getPlayerInteractor
    private val getTrack=Creator.getTrack
    private lateinit var playBtn: ImageButton
    private lateinit var timePlayTV: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val updateUIRunnable = object : Runnable {
        override fun run() {
            timePlayTV.text = Utils.millisToMmSs(playerInteractor.getCurrentPosition())
            handler.postDelayed(this, UPDATE_UI)
        }
    }

    private fun preparePlayer(src: String) {
        playerInteractor.preparePlayer(src,{playBtn.isEnabled = true},{
            playBtn.setImageResource(R.drawable.play_btn)
            handler.removeCallbacks(updateUIRunnable)
            timePlayTV.text = getString(R.string.test_time_play)
        })
    }

    private fun startPlayer() {
        playerInteractor.play {
            playBtn.setImageResource(R.drawable.pause_btn)
            handler.post(updateUIRunnable)
        }
    }

    private fun pausePlayer() {
        playerInteractor.pause {
            playBtn.setImageResource(R.drawable.play_btn)
            handler.removeCallbacks(updateUIRunnable)
        }
    }

    private fun setBackListener() {
        val back = findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            finish()
        }
    }

    private fun playbackControl() {
        when (playerInteractor.getState()) {
            PlayerState.STATE_PLAYING -> {
                pausePlayer()
            }
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                startPlayer()
            }
            else -> {}
        }
    }


    private fun setInfo(rc: Int) {

        val bundle = intent.extras
        val json = bundle!!.getString(KEY_PLAYER_ACTIVITY)
        //if(json==null) finish()
        val track = getTrack.execute(json!!)

        playBtn = findViewById(R.id.playIB)
        if (track.previewUrl != null) {
            preparePlayer(track.previewUrl)
            playBtn.setOnClickListener { playbackControl() }
        }

        val trackTV = findViewById<TextView>(R.id.trackTV)
        trackTV.text = track.trackName
        val artistTV = findViewById<TextView>(R.id.artistTV)
        artistTV.text = track.artistName
        val timeTv = findViewById<TextView>(R.id.timeTv)
        timeTv.text = Utils.millisToMmSs(track.trackTimeMillis)
        if (track.collectionName.isNullOrEmpty()) {
            val albumGr = findViewById<Group>(R.id.albumGroup)
            albumGr.visibility = View.GONE
        } else {
            val albumTv = findViewById<TextView>(R.id.albumTv)
            albumTv.text = track.collectionName
        }
        if (track.releaseDate.isNullOrEmpty() || ((track.releaseDate.length) < 5)) {
            val yearGr = findViewById<Group>(R.id.yearGroup)
            yearGr.visibility = View.GONE
        } else {
            val yearTv = findViewById<TextView>(R.id.yearTv)
            yearTv.text = track.releaseDate.substring(0, 4)
        }
        val genreTv = findViewById<TextView>(R.id.genreTv)
        genreTv.text = track.primaryGenreName
        val countryTv = findViewById<TextView>(R.id.countryTv)
        countryTv.text = track.country

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
        playBtn = findViewById(R.id.playIB)
        timePlayTV = findViewById(R.id.timePlayTV)
        setBackListener()

    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateUIRunnable)
        playerInteractor.release()
    }
}