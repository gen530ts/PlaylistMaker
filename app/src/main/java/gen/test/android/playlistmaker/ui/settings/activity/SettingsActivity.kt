package gen.test.android.playlistmaker.ui.settings.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.ui.settings.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    
    private val settingsViewModel by lazy {
        ViewModelProvider(
            this,
            SettingsViewModel.getViewModelFactory()
        )[SettingsViewModel::class.java]
    }
    private fun setBackListener() {
        val backImg = findViewById<ImageView>(R.id.backImageView)
        backImg.setOnClickListener {
            finish()
        }
    }

    private fun setShareListener() {
        val share = findViewById<TextView>(R.id.shareTextView)
        share.setOnClickListener {settingsViewModel.sharingApp() }
    }
    private fun setSupportListener() {
        val support = findViewById<TextView>(R.id.supportTextView)
        support.setOnClickListener {settingsViewModel.openSupport() }
    }
    private fun setAgreeListener() {
        val agree = findViewById<TextView>(R.id.agreeTextView)
        agree.setOnClickListener {settingsViewModel.openTerms() }
    }





    private fun setDarkListener() {
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)
        settingsViewModel.getThemeLiveData().observe(this) {
           
            themeSwitcher.isChecked =  it.isDark
        }
        settingsViewModel.getTheme()
       
        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            settingsViewModel.switchTheme(checked)
        }
    }






    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setBackListener()
        setShareListener()
        setSupportListener()
        setAgreeListener()
        setDarkListener()
        

        
        
    }
}



