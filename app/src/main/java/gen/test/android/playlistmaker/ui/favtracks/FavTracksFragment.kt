package gen.test.android.playlistmaker.ui.favtracks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.databinding.FragmentFavTracksBinding
import gen.test.android.playlistmaker.domain.models.Track
import gen.test.android.playlistmaker.ui.player.activity.PlayerFragment
import gen.test.android.playlistmaker.ui.search.activity.TrackSearchAdapter
import gen.test.android.playlistmaker.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavTracksFragment : Fragment() {

    private val viewModel: FavTracksViewModel by viewModel()
    private lateinit var binding: FragmentFavTracksBinding
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TrackSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = binding.tracksList
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = TrackSearchAdapter { startPlayerFragment(it) }
        recycler.adapter = adapter
        viewModel.observeData().observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Success -> showDataScreen(it)
                is ScreenState.Warning -> showWarningScreen()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.findFavorites()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showDataScreen(tracks: ScreenState.Success<List<Track>>) {
        binding.apply {
            dataLL.isVisible = true
            emptyCL.isVisible = false
        }
        adapter.clearItems()
        val tempTracks = ArrayList<Track>()
        tempTracks.addAll(tracks.data)
        adapter.setItems(tempTracks)
        adapter.notifyDataSetChanged()
    }

    private fun showWarningScreen() {
        binding.apply {
            dataLL.isVisible = false
            emptyCL.isVisible = true
        }
    }

    private fun startPlayerFragment(track: Track) {
        findNavController().navigate(
            R.id.action_mediaFragment_to_playerFragment,
        PlayerFragment.createArgs(Gson().toJson(track))
        )
    }
}