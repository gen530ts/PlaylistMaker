package gen.test.android.playlistmaker.ui.player.activity


import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.databinding.ActivityPlayerBinding
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.ui.bottomsheetplaylists.PlBottomAdapter
import gen.test.android.playlistmaker.ui.createplaylist.CreatePlayListFragment
import gen.test.android.playlistmaker.ui.player.model.ModifyUI
import gen.test.android.playlistmaker.ui.player.view_model.PlayerViewModel
import gen.test.android.playlistmaker.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

private const val ROUNDED_CORNERS_PLAYER = 8f
const val KEY_PLAYER_ACTIVITY = "KEY_PLAYER_ACTIVITY"

class PlayerActivity : AppCompatActivity() {

    private val viewModel by viewModel<PlayerViewModel>()
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var playBtn: ImageButton
    private lateinit var timePlayTV: TextView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter:PlBottomAdapter
    private var currentPlist=""

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
                recycler = listPlBotSh

                timeTv.text = Utils.millisToMmSs(it.trackTimeMillis)
                favoriteIB.setOnClickListener { viewModel.onFavoriteClicked() }
                addToPl.setOnClickListener { viewBottomSheet() }
                playListsBt.setOnClickListener {
                    val fragment = CreatePlayListFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.playerFragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
            recycler.layoutManager = LinearLayoutManager(this)
            val filePath = File(getExternalFilesDir(
                Environment
                .DIRECTORY_PICTURES),"playlistmaker_album")
            adapter=PlBottomAdapter({ et -> addTrackToPlayList(et) },filePath)
            recycler.adapter=adapter
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
        viewModel.observeData().observe(this) {
            when (it) {
                is ScreenState.Success -> {
                    adapter.setData(it.data)
                    adapter.notifyDataSetChanged()
                }
                else -> {}
            }
        }
        viewModel.observeTrackToPlist().observe(this) {
            when (it) {
                is ScreenState.Success -> if (it.data > 0) {
                    bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
                    Toast.makeText(this, "Добавлено в плейлист $currentPlist", Toast.LENGTH_LONG).show()
                } else Toast.makeText(this, "Трек уже добавлен в плейлист $currentPlist", Toast
                    .LENGTH_LONG).show()

                else -> {}
            }

        }


        if (json != null) {
            viewModel.prepare(json)
        }
    }

    private fun viewBottomSheet() {
        viewModel.findPlaylists()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun addTrackToPlayList(plist: Plist) {
        currentPlist=plist.name
        viewModel.addTrackToPlist(plist)
        viewModel.resetAddTrackToPlist()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.addPlBottomSheet)
        setInfo(Utils.dpToPx(ROUNDED_CORNERS_PLAYER, this))
        setBackListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

}

