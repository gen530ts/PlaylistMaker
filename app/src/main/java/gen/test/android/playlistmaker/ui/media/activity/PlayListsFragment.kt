package gen.test.android.playlistmaker.ui.media.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import gen.test.android.playlistmaker.databinding.FragmentPlayListsBinding
import gen.test.android.playlistmaker.ui.media.view_model.PlayListsViewModel
import gen.test.android.playlistmaker.utils.ScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListsFragment : Fragment() {

    private val viewModel: PlayListsViewModel by viewModel()

    companion object {
        fun newInstance() = PlayListsFragment()
    }

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
        viewModel.observeData().observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Success -> showTestScreen(it)
                is ScreenState.Warning -> showWarningScreen()
            }
        }
    }

    private fun showTestScreen(it: ScreenState.Success<String>) {
        binding.apply {
            playListsBt.isVisible = false
            playListsIV.isVisible = false
            playListsTV.text = it.data
        }
    }

    private fun showWarningScreen() {
        binding.apply {
            playListsBt.isVisible = true
            playListsIV.isVisible = true
        }
    }
}