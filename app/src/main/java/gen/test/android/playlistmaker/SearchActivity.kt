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
private const val RESPONSE_OK = 200
class SearchActivity : AppCompatActivity() {

    private lateinit var   searchHistory:SearchHistory
    private var searchTxt = ""
    private var searchEdit: EditText? = null
    private var comProblemLL: LinearLayout? = null
    private var notFoundLL: LinearLayout? = null
    private var updateRequestBtn: Button? = null
    private var historySearchLL: LinearLayout? = null
    private var tracksListLL: LinearLayout? = null
    private var clearHistoryBtn: Button? = null
    private var baseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val musicService = retrofit.create(ItunesAppleApi::class.java)
    private val tracks = ArrayList<Track>()
    private val tracksHistory = ArrayList<Track>()
    private val adapter = TrackSearchAdapter{searchHistory.add(it) }
    private val adapterHistory = TrackSearchAdapter{}

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
                        RESPONSE_OK -> {
                            if (response.body()?.results?.isNotEmpty() == true) {
                                tracks.addAll(response.body()?.results!!)
                                goneAll(tracksListLL)
                            } else {
                                goneAll(notFoundLL)
                            }
                        }
                        else -> {
                            goneAll(comProblemLL)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    tracks.clear()
                    adapter.notifyDataSetChanged()
                    goneAll(comProblemLL)
                }
            })
    }

    private fun goneAll(view:LinearLayout?){
        val views = listOf(tracksListLL, notFoundLL, comProblemLL,historySearchLL)
        views.forEach {
            when(it){
                view->it?.visibility = View.VISIBLE
                else->it?.visibility = View.GONE
            }
        }
    }

            private fun viewHistory(){
        val lArr=searchHistory.read()
        if (lArr.isNotEmpty()){
            tracksHistory.clear()
            tracksHistory.addAll(lArr)
            adapterHistory.notifyDataSetChanged()
            goneAll(historySearchLL)
        }
    }

    private fun setListeners() {
        val clear = findViewById<ImageView>(R.id.clearImageView)
        searchEdit = findViewById(R.id.searchEditText)
        notFoundLL = findViewById(R.id.notFoundLL)
        comProblemLL = findViewById(R.id.comProblemLL)
        tracksListLL = findViewById(R.id.tracksListLL)
        updateRequestBtn = findViewById(R.id.updateRequestBtn)
        updateRequestBtn?.setOnClickListener {search()}
        searchEdit?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
               // true
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
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = clearButtonVisibility(s)
                searchTxt = s.toString()
                if ((searchEdit?.hasFocus() == true) && (s?.isEmpty()==true)) {viewHistory()}
                else goneAll(null)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }
        searchEdit?.addTextChangedListener(simpleTextWatcher)
        searchEdit?.setOnFocusChangeListener{ _, hasFocus ->if(hasFocus&&(searchEdit!!.text.isEmpty()))
            viewHistory()}

        historySearchLL=findViewById(R.id.historySearchLL)
        clearHistoryBtn= findViewById(R.id.clearHistoryBtn)
        clearHistoryBtn?.setOnClickListener {searchHistory.clearHistory()
        historySearchLL?.visibility=View.GONE}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setListeners()
        searchHistory = SearchHistory((application as App).sharedPrefs)

        val recycler = findViewById<RecyclerView>(R.id.tracksList)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter.setItems(tracks)
        recycler.adapter = adapter

        val recyclerHistory = findViewById<RecyclerView>(R.id.historySearchList)
        recyclerHistory.layoutManager = LinearLayoutManager(this)
        adapterHistory.setItems(tracksHistory)
        recyclerHistory.adapter = adapterHistory


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

