package gen.test.android.playlistmaker.domain.sharing

import gen.test.android.playlistmaker.domain.models.PlistViewScreen

interface SharingInteractor {
    fun shareApp()
    fun openTerms()
    fun openSupport()
    fun sharePlist(plistViewScreen: PlistViewScreen)
}