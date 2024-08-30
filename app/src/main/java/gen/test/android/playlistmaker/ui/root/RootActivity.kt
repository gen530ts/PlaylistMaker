package gen.test.android.playlistmaker.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gen.test.android.playlistmaker.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*if (savedInstanceState == null) {
            supportFragmentManager.commit {
                this.add(
                    R.id.rootFragmentContainerView,
                    MediaFragment()
                )
            }
        }*/
    }
}