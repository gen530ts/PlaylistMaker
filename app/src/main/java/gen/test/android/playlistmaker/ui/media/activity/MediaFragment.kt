package gen.test.android.playlistmaker.ui.media.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.databinding.FragmentMediaBinding


class MediaFragment : Fragment() {
    private lateinit var binding: FragmentMediaBinding

    private lateinit var tabMediator: TabLayoutMediator
    private fun setBackListener() {
        //val backImg = findViewById<ImageView>(R.id.backImageView)
        binding.backImageView.setOnClickListener {
//            findNavController().navigate(R.id.action_mediaFragment_to_searchFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setBackListener()
        binding.viewPager.adapter = MediaViewPagerAdapter(childFragmentManager,
            lifecycle)

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.fav_tracks)
                1 -> tab.text = getString(R.string.play_lists)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }
}

/*lifecycle
parentFragmentManager.commit {
    replace(
// Указали, в каком контейнере работаем
        R.id.rootFragmentContainerView,
// Создали фрагмент
        SettingsFragment(),
// Указали тег фрагмента
        "tag"
    )
}*/
