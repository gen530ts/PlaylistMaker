package gen.test.android.playlistmaker

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.button1)

        val btn1ClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажали на кнопку1!", Toast.LENGTH_SHORT).show()
            }
        }

        btn1.setOnClickListener(btn1ClickListener)
        val btn2 = findViewById<Button>(R.id.button2)

        btn2.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажали на кнопку2!", Toast.LENGTH_SHORT)
                .show()
        }
        val btn3 = findViewById<Button>(R.id.button3)
        btn3.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажали на кнопку3!", Toast
                .LENGTH_SHORT)
                .show()
        }

    }
}
