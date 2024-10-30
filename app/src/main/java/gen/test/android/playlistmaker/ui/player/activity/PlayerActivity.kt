package gen.test.android.playlistmaker.ui.player.activity


import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.databinding.ActivityPlayerBinding
import gen.test.android.playlistmaker.ui.player.model.ModifyUI
import gen.test.android.playlistmaker.ui.player.view_model.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ROUNDED_CORNERS_PLAYER = 8f
const val KEY_PLAYER_ACTIVITY = "KEY_PLAYER_ACTIVITY"

class PlayerActivity : AppCompatActivity() {

    private val viewModel by viewModel<PlayerViewModel>()
    private lateinit var binding: ActivityPlayerBinding
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
        playBtn = binding.playIB
        timePlayTV = binding.timePlayTV

        viewModel.getTrackLD().observe(this) {
            if (it.previewUrl != null) {
                viewModel.preparePlayer(it.previewUrl)
                playBtn.setOnClickListener { viewModel.playbackControl() }
            }
            binding.apply {
                trackTV.text = it.trackName
                artistTV.text = it.artistName
                genreTv.text = it.primaryGenreName
                countryTv.text = it.country
                timeTv.text = Utils.millisToMmSs(it.trackTimeMillis)
                favoriteIB.setOnClickListener { viewModel.onFavoriteClicked() }
            }

            if (it.collectionName.isNullOrEmpty()) {
                binding.albumGroup.isVisible = false
            } else {
                binding.albumTv.text = it.collectionName
            }
            if (it.releaseDate.isNullOrEmpty() || ((it.releaseDate.length) < 5)) {

                binding.yearGroup.isVisible = false
            } else {
                binding.yearTv.text = it.releaseDate.substring(0, 4)
            }

            Glide.with(this)
                .load(it.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .fitCenter()
                .placeholder(R.drawable.placeholder_player)
                .transform(RoundedCorners(rc))
                .into(binding.playerIV)
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
        viewModel.getFavorite().observe(this) {
            binding.favoriteIB.setImageResource(
                if (it) R.drawable.is_favorite
                else R.drawable.isnot_favorite
            )
        }
        if (json != null) {
            viewModel.prepare(json)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInfo(Utils.dpToPx(ROUNDED_CORNERS_PLAYER, this))
        setBackListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

}