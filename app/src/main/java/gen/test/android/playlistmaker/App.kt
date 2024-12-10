package gen.test.android.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.markodevcic.peko.PermissionRequester
import gen.test.android.playlistmaker.di.dataModule
import gen.test.android.playlistmaker.di.interactorModule
import gen.test.android.playlistmaker.di.repositoryModule
import gen.test.android.playlistmaker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

const val SHARED_PREFERENCES = "shared_preferences"
const val DARK_THEME_KEY = "key_dark_theme"

class App : Application() {
    var darkTheme = false

   private lateinit var sharedPrefs: SharedPreferences


    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        sharedPrefs.edit()
            .putBoolean(DARK_THEME_KEY, darkTheme)
            .apply()
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
        sharedPrefs = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(DARK_THEME_KEY, false) == true
        switchTheme(darkTheme)
        PermissionRequester.initialize(applicationContext)
    }
}