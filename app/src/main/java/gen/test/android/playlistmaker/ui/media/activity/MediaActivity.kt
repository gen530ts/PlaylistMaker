package gen.test.android.playlistmaker.ui.media.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaBinding

    private lateinit var tabMediator: TabLayoutMediator
    private fun setBackListener() {
        //val backImg = findViewById<ImageView>(R.id.backImageView)
        binding.backImageView.setOnClickListener {
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackListener()
        binding.viewPager.adapter = MediaViewPagerAdapter(supportFragmentManager,
            lifecycle)

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.fav_tracks)
                1 -> tab.text = getString(R.string.play_lists)
            }
        }
        tabMediator.attach()

    }
    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}