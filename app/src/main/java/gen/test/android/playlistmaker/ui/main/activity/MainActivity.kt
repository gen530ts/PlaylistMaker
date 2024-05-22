package gen.test.android.playlistmaker.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import gen.test.android.playlistmaker.MediaActivity
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.ui.search.activity.SearchActivity
import gen.test.android.playlistmaker.ui.settings.activity.SettingsActivity

class MainActivity : AppCompatActivity() {
    private fun setSearchBtnListener(){
        val searchBtn = findViewById<Button>(R.id.searchButton)
        val btn1ClickListener: View.OnClickListener = View.OnClickListener {
            val intent = Intent(this@MainActivity,SearchActivity::class.java)
            startActivity(intent)
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
