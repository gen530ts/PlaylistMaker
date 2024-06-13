package gen.test.android.playlistmaker.data.sharing.impl

import android.content.Context
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.data.sharing.SharingRepository
import gen.test.android.playlistmaker.domain.sharing.model.EmailData

class SharingRepositoryImpl(private val context: Context) : SharingRepository {
    override fun getShareAppLink(): String {
        return context.getString(R.string.share)
    }

    override fun getSupportEmailData(): EmailData {
        return EmailData(
            context.getString(R.string.name_email),
            context.getString(R.string.subject_email),
            context.getString(R.string.txt_email))
    }

    override fun getTermsLink(): String {
        return context.getString(R.string.link_oferta)
    }

}