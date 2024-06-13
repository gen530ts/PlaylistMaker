package gen.test.android.playlistmaker.data.settings.impl

import android.app.Application
import gen.test.android.playlistmaker.App
import gen.test.android.playlistmaker.domain.settings.ThemeRepository
import gen.test.android.playlistmaker.domain.settings.model.ThemeSettings

class ThemeRepositoryImpl(
    private val application:Application,
    
    )
    : ThemeRepository {

    override fun getTheme(): ThemeSettings = ThemeSettings((application as App).darkTheme)

    override fun switchTheme(themeSettings:ThemeSettings) {
        (application as App).switchTheme(themeSettings.isDark)
    }
}