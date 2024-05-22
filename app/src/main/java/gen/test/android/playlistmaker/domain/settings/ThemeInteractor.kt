package gen.test.android.playlistmaker.domain.settings

import gen.test.android.playlistmaker.domain.settings.model.ThemeSettings

interface ThemeInteractor {
    fun getTheme(): ThemeSettings
    fun switchTheme(themeSettings: ThemeSettings)
}