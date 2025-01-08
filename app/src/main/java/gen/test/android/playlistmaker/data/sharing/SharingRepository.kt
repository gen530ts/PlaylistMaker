package gen.test.android.playlistmaker.data.sharing

import gen.test.android.playlistmaker.domain.models.PlistViewScreen
import gen.test.android.playlistmaker.domain.sharing.model.EmailData

interface SharingRepository {
    fun getShareAppLink(): String
    fun getSupportEmailData(): EmailData
    fun getTermsLink(): String
    fun getSharePlistString(plistViewScreen: PlistViewScreen):String
}