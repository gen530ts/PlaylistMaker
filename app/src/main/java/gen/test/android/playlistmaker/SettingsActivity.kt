package gen.test.android.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private fun setBackListener(){
        val backImg=findViewById<ImageView>(R.id.backImageView)
        backImg.setOnClickListener {
            finish()
            /*val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setBackListener()
    }
}