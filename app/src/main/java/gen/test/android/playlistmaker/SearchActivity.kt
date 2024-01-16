package gen.test.android.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    private var savedState=""
    private lateinit var searchEdit:EditText
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun setListeners() {
        val clear = findViewById<ImageView>(R.id.clearImageView)
        searchEdit = findViewById(R.id.searchEditText)

        clear.visibility = View.GONE
        clear.setOnClickListener {
            searchEdit.text.clear()
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)

            Log.d("MyTag", "clearClick")
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = clearButtonVisibility(s)
                savedState=s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        searchEdit.addTextChangedListener(simpleTextWatcher)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("info",savedState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedState= savedInstanceState.getString("info").toString()
        searchEdit.setText(savedState)
    }
}
