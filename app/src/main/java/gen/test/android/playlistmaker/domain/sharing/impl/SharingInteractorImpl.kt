package gen.test.android.playlistmaker.domain.sharing.impl

import gen.test.android.playlistmaker.data.sharing.ExternalNavigator
import gen.test.android.playlistmaker.data.sharing.SharingRepository
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor
import gen.test.android.playlistmaker.domain.sharing.model.EmailData

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
    private val sharingRepository: SharingRepository
): SharingInteractor {

    companion object{
        /*private const val SHARE_APP_MESSAGE = "https://practicum.yandex.ru/android-developer/"
        private const val CHOOSER_TITLE="Выберите приложение"
        //private const val SHARE_TYPE = "text/plain"
        private const val SUPPORT_NAME_EMAIL="gnd011@yandex.ru"
        private const val SUPPORT_SUBJECT_EMAIL="Сообщение разработчикам и разработчицам приложения Playlist Maker"
        private const val SUPPORT_TXT_EMAIL="Спасибо разработчикам и разработчицам за крутое приложение!"
        private const val LINK_OFERTA="https://yandex.ru/legal/practicum_b2b_subscription_offer/"*/

    }
    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String = sharingRepository.getShareAppLink()

    private fun getSupportEmailData(): EmailData = sharingRepository.getSupportEmailData()

    private fun getTermsLink(): String = sharingRepository.getTermsLink()
}