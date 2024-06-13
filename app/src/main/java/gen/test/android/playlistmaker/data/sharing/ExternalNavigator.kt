package gen.test.android.playlistmaker.data.sharing

import gen.test.android.playlistmaker.domain.sharing.model.EmailData

interface ExternalNavigator {
    fun shareLink(shareApp: String)
    fun openLink(termsLink: String)
    fun openEmail(supportEmail: EmailData)
}