package gen.test.android.playlistmaker.domain.sharing.impl

import gen.test.android.playlistmaker.data.sharing.ExternalNavigator
import gen.test.android.playlistmaker.data.sharing.SharingRepository
import gen.test.android.playlistmaker.domain.sharing.SharingInteractor
import gen.test.android.playlistmaker.domain.sharing.model.EmailData

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
    private val sharingRepository: SharingRepository
): SharingInteractor {


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