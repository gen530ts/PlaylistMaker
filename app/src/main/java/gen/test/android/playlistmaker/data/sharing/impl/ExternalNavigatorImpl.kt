package gen.test.android.playlistmaker.data.sharing.impl

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import gen.test.android.playlistmaker.data.sharing.ExternalNavigator
import gen.test.android.playlistmaker.domain.sharing.model.EmailData

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    private val chooserTitle = "Выберите приложение"
    override fun shareLink(shareApp: String) {

        val shareIntent = Intent(Intent.ACTION_SEND)
        val shareIntent1 = Intent.createChooser(shareIntent, chooserTitle)
        shareIntent1.putExtra(Intent.EXTRA_TEXT, shareApp)
        shareIntent1.addFlags(FLAG_ACTIVITY_NEW_TASK)
       
        context.applicationContext.startActivity(shareIntent1)
    }

    override fun openLink(termsLink: String) {
        val uri: Uri = Uri.parse(termsLink)
        val agreeIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(
            context, Intent.createChooser(agreeIntent, chooserTitle).addFlags
                (FLAG_ACTIVITY_NEW_TASK), null
        )
    }

    override fun openEmail(supportEmail: EmailData) {
        val uri: Uri = Uri.parse("mailto:")
        val shareIntent = Intent(Intent.ACTION_SENDTO, uri)
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail.nameEmail))
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, supportEmail.subjectEmail)
        shareIntent.putExtra(Intent.EXTRA_TEXT, supportEmail.txtEmail)
        startActivity(
            context,
            Intent.createChooser(shareIntent, chooserTitle).addFlags(FLAG_ACTIVITY_NEW_TASK), null
        )
    }
}