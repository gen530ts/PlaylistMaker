package gen.test.android.playlistmaker.ui.media.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
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


    }
    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}