package gen.test.android.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.button1)

        val btn1ClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }

        btn1.setOnClickListener(btn1ClickListener)
        val btn2 = findViewById<Button>(R.id.button2)

        btn2.setOnClickListener {
            val intent = Intent(this, MediaActivity::class.java)
            startActivity(intent)

        }
        val btn3 = findViewById<Button>(R.id.button3)
        btn3.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

    }
}
