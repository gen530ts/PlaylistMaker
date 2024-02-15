package gen.test.android.playlistmaker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val KEY_DATA = "info"

class SearchActivity : AppCompatActivity() {
    private var searchTxt = ""
    private var searchEdit: EditText? = null
    private var comProblemLL: LinearLayout? = null
    private var notFoundLL: LinearLayout? = null
    private var updateRequestBTN: Button? = null
    private val baseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val musicService = retrofit.create(ItunesAppleApi::class.java)
    private val tracks = ArrayList<Track>()
    private val adapter = TrackSearchAdapter()

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun search() {
        musicService.search(searchEdit?.text.toString())
            .enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>,
                ) {
                    tracks.clear()
                    when (response.code()) {
                        200 -> {

                            if (response.body()?.results?.isNotEmpty() == true) {

                                tracks.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                                notFoundLL?.visibility = View.GONE
                                comProblemLL?.visibility = View.GONE
                                //showMessage("", "")
                            } else {
                                notFoundLL?.visibility = View.VISIBLE
                                comProblemLL?.visibility = View.GONE


                            }

                        }
                        else -> {
                            notFoundLL?.visibility = View.GONE
                            comProblemLL?.visibility = View.VISIBLE
                        }

                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    notFoundLL?.visibility = View.GONE
                    comProblemLL?.visibility = View.VISIBLE

                }
            })
    }


    private fun setListeners() {
        val clear = findViewById<ImageView>(R.id.clearImageView)
        searchEdit = findViewById(R.id.searchEditText)
        notFoundLL = findViewById(R.id.notFoundLL)
        comProblemLL = findViewById(R.id.comProblemLL)
        updateRequestBTN = findViewById(R.id.updateRequestBTN)
        updateRequestBTN?.setOnClickListener {search()}
        searchEdit?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
// ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                search()

                true
            }
            false
        }
        val imageBack = findViewById<ImageView>(R.id.backImageView)
        imageBack.setOnClickListener { finish() }
        clear.visibility = View.GONE
        clear.setOnClickListener {
            searchEdit?.text?.clear()
            tracks.clear()
            adapter.notifyDataSetChanged()
            val manager: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
// empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = clearButtonVisibility(s)
                searchTxt = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
// empty
            }
        }
        searchEdit?.addTextChangedListener(simpleTextWatcher)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setListeners()
        val recycler = findViewById<RecyclerView>(R.id.tracksList)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter.tracks = tracks
        recycler.adapter = adapter


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_DATA, searchTxt)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle,
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        searchTxt = savedInstanceState.getString(KEY_DATA).toString()
        searchEdit?.setText(searchTxt)
    }
}
