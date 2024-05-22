package gen.test.android.playlistmaker.domain.settings.impl

import gen.test.android.playlistmaker.domain.settings.ThemeInteractor
import gen.test.android.playlistmaker.domain.settings.ThemeRepository
import gen.test.android.playlistmaker.domain.settings.model.ThemeSettings

class ThemeInteractorImpl(private val themeRepository: ThemeRepository): ThemeInteractor {
    override fun getTheme(): ThemeSettings=themeRepository.getTheme()


    override fun switchTheme(themeSettings: ThemeSettings) {
        themeRepository.switchTheme(themeSettings)
    }

}