package gen.test.android.playlistmaker.ui.media.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import gen.test.android.playlistmaker.utils.ScreenState
import gen.test.android.playlistmaker.databinding.FragmentFavTracksBinding
import gen.test.android.playlistmaker.ui.media.view_model.FavTracksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavTracksFragment : Fragment() {

    private val viewModel: FavTracksViewModel by viewModel()

    companion object {
        fun newInstance() = FavTracksFragment()
    }


    private lateinit var binding: FragmentFavTracksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavTracksBinding.inflate(inflater, container, false)
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

    private fun showTestScreen(it: ScreenState.Success<Int>) {
        binding.apply {
            favTracksTV.text=it.data.toString()
            favTracksIV.isVisible = false
        }
    }

    private fun showWarningScreen() {
        binding.apply {
            favTracksTV.isVisible = true
            favTracksIV.isVisible = true
        }
    }

}