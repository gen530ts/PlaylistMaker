package gen.test.android.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private var chooserTitle:String = ""

    private fun setBackListener() {
        val backImg = findViewById<ImageView>(R.id.backImageView)
        backImg.setOnClickListener {
            finish()
        }
    }

    private fun setShareListener() {
        val share = findViewById<TextView>(R.id.shareTextView)
        share.setOnClickListener {
            val txtMessage = resources.getString(R.string.txt_Message)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, txtMessage)
            shareIntent.type="text/plain"
           // shareIntent.putExtra(Intent.EXTRA_COMPONENT_NAME,
           // shareIntent.putExtra(Intent.EXTRA_PACKAGE_NAME,"com.android.messaging")
            //shareIntent.putExtra(Intent.EXTRA_PACKAGE_NAME
           // shareIntent.`package`="com.android.messaging"
            startActivity(Intent.createChooser(shareIntent, chooserTitle))
        }
    }

    private fun setSupportListener() {
        val support = findViewById<TextView>(R.id.supportTextView)
        support.setOnClickListener {
            val  uri: Uri =Uri.parse("mailto:")
            val shareIntent = Intent(Intent.ACTION_SENDTO,uri)
           // shareIntent.putExtra(Intent.EXTRA_, resources.getString(R.string.nameEmail))
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.nameEmail)) )
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,  resources.getString(R.string.subjectEmail))
            shareIntent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.txtEmail) )
            //shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, chooserTitle))
        }
    }
    private fun setAgreeListener() {
        val agree = findViewById<TextView>(R.id.agreeTextView)
        agree.setOnClickListener {
          val  uri: Uri =Uri.parse(resources.getString(R.string.linkOferta))
            val agreeIntent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(Intent.createChooser(agreeIntent, chooserTitle))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        chooserTitle = resources.getString(R.string.chooser_Title)
        setBackListener()
        setShareListener()
        setSupportListener()
        setAgreeListener()
    }
}

//val txtMessage = resources.getString(R.string.txt_Message)
//shareIntent.putExtra(Intent.EXTRA_TEXT, txtMessage)
//shareIntent.type = "text/plain"
//val message = "Привет, Android-разработка — это круто!"
// val myUri: Uri = Uri.parse("www.google.com")
// "<p>Visit <a href=\"http://www.google"+
//  ".com\">google</a> for more info.</p>")
//shareIntent.putExtra(Intent.EXTRA_HTML_TEXT
// shareIntent.putExtra(Intent.EXTRA_TEXT, "hi, $myUri")
// val uri:URI =URI.pa
//shareIntent.type = "text/plain"
//shareIntent.component=
//shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
//  +
//    ")
//shareIntent.putExtra(Intent.EXTRA_TEXT, message)

/*val sendIntent: Intent = Intent().apply {
action = Intent.ACTION_SEND
putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
type = "text/plain"
}*/


