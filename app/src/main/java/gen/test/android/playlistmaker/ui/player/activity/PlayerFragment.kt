package gen.test.android.playlistmaker.ui.player.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.Utils
import gen.test.android.playlistmaker.databinding.FragmentPlayerBinding
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.ui.bottomsheetplaylists.PlBottomAdapter
import gen.test.android.playlistmaker.ui.player.model.ModifyUI
import gen.test.android.playlistmaker.ui.player.view_model.PlayerViewModel
import gen.test.android.playlistmaker.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

//import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : Fragment() {
    companion object {
        private const val ROUNDED_CORNERS_PLAYER = 8f
        private const val KEY_PLAYER_FRAGMENT = "KEY_PLAYER_FRAGMENT"
        fun createArgs(fact: String): Bundle =
            bundleOf(KEY_PLAYER_FRAGMENT to fact)
    }

    private val viewModel by viewModel<PlayerViewModel>()
    private lateinit var binding: FragmentPlayerBinding
    private lateinit var playBtn: ImageButton
    private lateinit var timePlayTV: TextView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var recycler: RecyclerView
    private var currentPlist = ""

    private fun setBackListener() {
        val back = binding.backButton
        //findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            //requireActivity().onBackPressedDispatcher.onBackPressed()
            findNavController().popBackStack()
        }
    }

    private fun setInfo(rc: Int) {

        val json = requireArguments().getString(KEY_PLAYER_FRAGMENT)
        //= bundle!!.getString(KEY_PLAYER_ACTIVITY)
        playBtn = binding.playIB
        timePlayTV = binding.timePlayTV

        viewModel.getTrackLD().observe(viewLifecycleOwner) {
            if (it.previewUrl != null) {
                Toast.makeText(requireContext(), "viewModel.preparePlayer", Toast.LENGTH_LONG).show()
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
                    findNavController().navigate(
                        R.id.action_playerFragment_to_createPlayListFragment,
                        null
                    )

/*
                    val fragment = CreatePlayListFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.playerFragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit()*/
                    //bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

                }
            }
            recycler.layoutManager = LinearLayoutManager(requireContext())
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
        viewModel.getModUI().observe(viewLifecycleOwner) {
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
        viewModel.getFavorite().observe(viewLifecycleOwner) {
            binding.favoriteIB.setImageResource(
                if (it) R.drawable.is_favorite
                else R.drawable.isnot_favorite
            )
        }
        viewModel.observeData().observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Success -> recycler.adapter = PlBottomAdapter(
                    it.data
                ) { et -> addTrackToPlayList(et) }
                else -> {}
            }
            /*Toast.makeText(this, "Click on item ${et.name}", Toast.LENGTH_LONG).show()*/
        }
        viewModel.observeTrackToPlist().observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Success -> if (it.data > 0) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    Toast.makeText(
                        requireContext(),
                        "Добавлено в плейлист $currentPlist",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else Toast.makeText(
                    requireContext(), "Трек уже добавлен в плейлист $currentPlist", Toast
                        .LENGTH_LONG
                ).show()

                else -> {}
            }

        }


        if (json != null) {
            //Toast.makeText(requireContext(), "viewModel.prepare($json)", Toast.LENGTH_LONG).show()
            Log.d("mytag", "viewModel.prepare(${json.take(10)})")
            viewModel.prepare(json)
        }else {
            //Toast.makeText(requireContext(), "json = null", Toast.LENGTH_LONG).show()
            Log.d("mytag", "viewModel.not prepare(json=null)")
        }
    }

    private fun viewBottomSheet() {
        viewModel.findPlaylists()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun addTrackToPlayList(plist: Plist) {
        currentPlist = plist.name
        viewModel.addTrackToPlist(plist)
        viewModel.resetAddTrackToPlist()
    }

    /*    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityPlayerBinding.inflate(layoutInflater)
            setContentView(binding.root)
            bottomSheetBehavior = BottomSheetBehavior.from(binding.addPlBottomSheet)
            setInfo(Utils.dpToPx(ROUNDED_CORNERS_PLAYER, this))
            setBackListener()
        }*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("mytag", "onCreateView:$this")
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.overlay.visibility = View.GONE
        Log.d("mytag", "onViewCreated:$this")
        bottomSheetBehavior = BottomSheetBehavior.from(binding.addPlBottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN ,BottomSheetBehavior.STATE_COLLAPSED-> {
                            binding.overlay.visibility = View.GONE
                        }

                        else -> {
                            binding.overlay.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            }
        )
        setInfo(Utils.dpToPx(ROUNDED_CORNERS_PLAYER, requireContext()))
        setBackListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("mytag", "onCreate:$this")
    }
    override fun onStart() {
        super.onStart()
        Log.d("mytag", "onStart:$this")
    }

    override fun onResume() {
        super.onResume()
        Log.d("mytag", "onResume:$this")
    }

    override fun onStop() {
        super.onStop()
        Log.d("mytag", "onStop:$this")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("mytag", "onDestroyView:$this")

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("mytag", "onDestroy:$this")
    }
    override fun onPause() {
        Log.d("mytag", "onPause:$this")
        super.onPause()
        //viewModel.get
        viewModel.pausePlayer()
    }
}