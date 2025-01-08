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
import gen.test.android.playlistmaker.ui.editplaylist.EditPlayListFragment
import gen.test.android.playlistmaker.ui.player.activity.PlayerFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PlistViewFragment : Fragment() {

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
        initBottomSheetsMenu()
        binding.shareImageView.setOnClickListener {sharePlist()}
        binding.shareTVBSMenu.setOnClickListener { sharePlist() }
        binding.deletePListTVBSMenu.setOnClickListener { delPlist() }
        binding.editInfoTVBSMenu.setOnClickListener { editPlist() }
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

                    isTracksInPlist = true

                    items.addAll(it.tracks)

                } else {
                    isTracksInPlist = false
                    Toast.makeText(requireContext(),"В этом плейлисте нет " +
                            "треков",Toast.LENGTH_LONG).show()
                }
                tracksAdapter?.setItems(items)
                tracksAdapter?.notifyDataSetChanged()
            }else requireActivity().onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun editPlist() {
        findNavController().navigate(
            R.id.action_plistViewFragment_to_editPlayListFragment,
            EditPlayListFragment.createArgs(requireArguments().getLong(PLIST_VIEW_FRAGMENT))
        )
    }

    private fun sharePlist() {
        bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
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
        findNavController().navigate(
            R.id.action_plistViewFragment_to_playerFragment,
            PlayerFragment.createArgs(Gson().toJson(track))
        )
    }

    private fun delTrack(id: Int) {
        confirmDialog.setTitle("Удалить трек")
            .setMessage("Хотите удалить трек?")
            .setNegativeButton("Нет") { _, _ -> }
            .setPositiveButton("Да") { _, _ ->
                viewModel.delTrackInPlist(id)
            }
            .show()
    }

    private fun delPlist() {
        bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
        confirmDialog.setTitle("Удалить плейлист")
            .setMessage("Хотите удалить плейлист?")
            .setNegativeButton("Нет") { _, _ -> }
            .setPositiveButton("Да") { _, _ ->
                viewModel.delPlist()
            }
            .show()
    }
}

