package gen.test.android.playlistmaker.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.databinding.FragmentPlistViewBinding
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.ui.player.activity.PlayerFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PlistViewFragment : Fragment() {

    //private val qw:Long=requireArguments().getLong(PLIST_VIEW_FRAGMENT)
    private val viewModel: PlistViewViewModel by viewModel {
        parametersOf(requireArguments().getLong(PLIST_VIEW_FRAGMENT))
    }

    companion object {
        private const val PLIST_VIEW_FRAGMENT = "PLIST_VIEW_FRAGMENT"
        fun createArgs(idPlist: Long): Bundle =
            bundleOf(PLIST_VIEW_FRAGMENT to idPlist)
    }

    private lateinit var binding: FragmentPlistViewBinding
    private var tracksAdapter: PlistViewAdapter? = null
    private lateinit var confirmDialog: MaterialAlertDialogBuilder
    private var isTracksInPlist=false
    private lateinit var bottomSheetBehaviorMenu: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlistViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backImageView.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.shareImageView.setOnClickListener {sharePlist()}
        binding.shareTVBSMenu.setOnClickListener { sharePlist() }
        binding.deletePListTVBSMenu.setOnClickListener { delPlist() }
        initBottomSheetsMenu()
        binding.moreImageView.setOnClickListener {
            bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        initBottomSheetsTracks()
        initRecycleView()
        confirmDialog = MaterialAlertDialogBuilder(requireContext(),R.style.CustomDialogTheme)

        viewModel.observePlist().observe(viewLifecycleOwner) {
            if(it.name!="") {
                binding.playlistNameTextView.text = it.name
                binding.pListBlockBSMenu.namePl.text = it.name
                if (it.imageUri != null) {
                    binding.playlistCoverPlayerImageView.setImageURI(it.imageUri)
                    binding.pListBlockBSMenu.coverPlIv.setImageURI(it.imageUri)
                }
                binding.playlistDescriptionTextView.text = it.description
                binding.tracksCountTextView.text = it.numberOfTracks
                binding.pListBlockBSMenu.numberTrack.text = it.numberOfTracks
                binding.tracksTimeTextView.text = it.durationAllTracks
                val items = arrayListOf<Track>()
                if (it.tracks.isNotEmpty()) {
                   /* val temp= mutableListOf<Track>()
                    temp+=it.tracks
                    temp.sortedBy { tr->tr.createAt }*/
                    isTracksInPlist = true

                    items.addAll(it.tracks)// += it.tracks  it.tracks as ArrayList<Track>
                    //Log.d("mytag", "items=$items")
                } else {
                    isTracksInPlist = false
                }
                tracksAdapter?.setItems(items)
                tracksAdapter?.notifyDataSetChanged()
                // Log.d("mytag", "it.tracks = ${it.tracks}")
            }else requireActivity().onBackPressedDispatcher.onBackPressed()
        }
//val tst= buildString {  }

    }

    private fun sharePlist() {
       if(isTracksInPlist) viewModel.sharePlist()
        else Toast.makeText(requireContext(),"В этом плейлисте нет " +
               "списка треков, которым можно поделиться",Toast.LENGTH_LONG).show()
    }

    private fun initBottomSheetsTracks() {
        var bottomSheetPeekHeight: Int
        val bottomSheetContainer = binding.bottomSheetTracksLinearLayout
        val bottomSheetBehaviorTracks = BottomSheetBehavior.from(bottomSheetContainer)
        val viewTreeObserver: ViewTreeObserver = binding.supportingViewTrack.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    if (binding.supportingViewTrack.width > 0 && binding.supportingViewTrack.height > 0) {
                        bottomSheetPeekHeight = binding.supportingViewTrack.height
                        bottomSheetBehaviorTracks.peekHeight = bottomSheetPeekHeight
                        if (viewTreeObserver.isAlive) {
                            viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    }

                }
            })
        }
        bottomSheetBehaviorTracks.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun initBottomSheetsMenu() {

        val bottomSheetContainer = binding.bottomSheetMenuLinearLayout
        bottomSheetBehaviorMenu = BottomSheetBehavior.from(bottomSheetContainer)

        bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehaviorMenu.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.isVisible = false
                        binding.bottomSheetTracksLinearLayout.isEnabled = true
                        binding.fragmentPlaylistViewConstraintLayout.isEnabled = true
                    }

                    else -> {
                        binding.overlay.isVisible = true
                        binding.bottomSheetTracksLinearLayout.isEnabled = false
                        binding.fragmentPlaylistViewConstraintLayout.isEnabled = false
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        //binding.bottomSheetMenu.playlistView.playlistNameTextView
    }

    private fun initRecycleView() {
        tracksAdapter = PlistViewAdapter(
            { delTrack(it.trackId) },
            { startPlayer(it) })



        binding.playlistsRecycleView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.playlistsRecycleView.adapter = tracksAdapter
    }

    private fun startPlayer(track: Track) {
        //Toast.makeText(requireContext(), "id=$id ", Toast.LENGTH_LONG).show()
        findNavController().navigate(
            R.id.action_plistViewFragment_to_playerFragment,
            PlayerFragment.createArgs(Gson().toJson(track))
        )
    }

    private fun delTrack(id: Int) {
        confirmDialog.setTitle("Удалить трек")
            .setMessage("Вы уверены, что хотите удалить трек из плейлиста?")
            .setNegativeButton("Отмена") { _, _ -> }
            .setPositiveButton("Удалить") { _, _ ->
                viewModel.delTrackInPlist(id)
            }
            .show()
    }

    private fun delPlist() {
        confirmDialog.setTitle("Удалить плейлист")
            .setMessage("Хотите удалить плейлист?")
            .setNegativeButton("Отмена") { _, _ -> }
            .setPositiveButton("Удалить") { _, _ ->
                viewModel.delPlist()
            }
            .show()
    }
}

//