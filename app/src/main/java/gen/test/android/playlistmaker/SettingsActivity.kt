package gen.test.android.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private val chooserTitle = "Выберите приложение"

    private fun setBackListener() {
        val backImg = findViewById<ImageView>(R.id.backImageView)
        backImg.setOnClickListener {
            finish()
        }
    }

    private fun setShareListener() {
        val share = findViewById<TextView>(R.id.shareTextView)
        share.setOnClickListener {
            val txtMessage = resources.getString(R.string.txt_message)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, txtMessage)
            shareIntent.type="text/plain"
            startActivity(Intent.createChooser(shareIntent, chooserTitle))
        }
    }

    private fun setSupportListener() {
        val support = findViewById<TextView>(R.id.supportTextView)
        support.setOnClickListener {
            val  uri: Uri =Uri.parse("mailto:")
            val shareIntent = Intent(Intent.ACTION_SENDTO,uri)
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.name_email)) )
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,  resources.getString(R.string.subject_email))
            shareIntent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.txt_email) )
            startActivity(Intent.createChooser(shareIntent, chooserTitle))
        }
    }
    private fun setAgreeListener() {
        val agree = findViewById<TextView>(R.id.agreeTextView)
        agree.setOnClickListener {
          val  uri: Uri =Uri.parse(resources.getString(R.string.link_oferta))
            val agreeIntent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(Intent.createChooser(agreeIntent, chooserTitle))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setBackListener()
        setShareListener()
        setSupportListener()
        setAgreeListener()
    }
}



