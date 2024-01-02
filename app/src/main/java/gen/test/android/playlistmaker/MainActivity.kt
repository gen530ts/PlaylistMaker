package gen.test.android.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private fun setSearchBtnListener(){
        val searchBtn = findViewById<Button>(R.id.searchButton)
        val btn1ClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        searchBtn.setOnClickListener(btn1ClickListener)
    }
    private fun setMediaBtnListener(){
        val mediaBtn = findViewById<Button>(R.id.mediaButton)
        mediaBtn.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            startActivity(intent)

        }
    }
    private fun setSettingsBtnListener(){
        val settingsBtn = findViewById<Button>(R.id.settingsButton)
        settingsBtn.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSearchBtnListener()
        setMediaBtnListener()
        setSettingsBtnListener()
    }
}
