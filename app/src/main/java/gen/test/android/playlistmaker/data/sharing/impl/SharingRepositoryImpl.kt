package gen.test.android.playlistmaker.data.sharing.impl

import android.content.Context
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.data.sharing.SharingRepository
import gen.test.android.playlistmaker.domain.models.PlistViewScreen
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

    override fun getSharePlistString(plistViewScreen: PlistViewScreen): String {
        val plistStringShare = buildString {
            append(plistViewScreen.name)
            appendLine()
            if (plistViewScreen.description.isNotEmpty()) {
                append(plistViewScreen.description)
                appendLine()
            }
            append(plistViewScreen.numberOfTracks)
            plistViewScreen.tracks.forEachIndexed { index, track ->
                appendLine()
                val trTime=getStringDuration(track.trackTimeMillis)
                append(
                    "${index + 1}. ${track.artistName} - ${track.trackName} (${trTime})"
                )
            }
        }
        return plistStringShare
    }

    private fun getStringDuration(timeMillis:Int):String{
        val number = timeMillis/60000
        val i = number % 100
        val i1 = i % 10
        val i2 = i / 10
        val first= "$number "
        val second= if ((i1 == 1) && (i2 != 1)) "минута"
        else if ((i1 > 1) && (i1 < 5) && (i2 != 1)) "минуты"
        else "минут"
        return  "$first$second"
    }
}