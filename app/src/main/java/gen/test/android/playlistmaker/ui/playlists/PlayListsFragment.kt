package gen.test.android.playlistmaker.ui.playlists

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.databinding.FragmentPlayListsBinding
import gen.test.android.playlistmaker.domain.models.Plist
import gen.test.android.playlistmaker.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PlayListsFragment : Fragment() {

    private val viewModel: PlayListsViewModel by viewModel()
    private lateinit var recycler: RecyclerView
    private lateinit var binding: FragmentPlayListsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = binding.playListsRecycler
        recycler.layoutManager = GridLayoutManager(requireContext(),2)

        binding.playListsBt.setOnClickListener { startCreatePL() }
        viewModel.observeData().observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Success -> showDataScreen(it)
                is ScreenState.Warning -> showWarningScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.findPlaylists()
    }

    private fun startCreatePL() {
        findNavController().navigate(
            R.id.action_mediaFragment_to_createPlayListFragment,
            null
        )
    }

    private fun showDataScreen(it: ScreenState.Success<List<Plist>>) {
        binding.apply {
            playListsTV.isVisible = false
            playListsIV.isVisible = false
            playListsRecycler.isVisible=true
            val filePath = File(requireContext().getExternalFilesDir(
                Environment
                .DIRECTORY_PICTURES),"playlistmaker_album")
            playListsRecycler.adapter= PlayListsAdapter(it.data,filePath)
        }
    }

    private fun showWarningScreen() {
        binding.apply {
            playListsTV.isVisible = true
            playListsIV.isVisible = true
            playListsRecycler.isVisible=false
        }
    }
}