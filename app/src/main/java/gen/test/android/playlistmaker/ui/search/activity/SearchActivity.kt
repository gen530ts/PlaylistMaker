package gen.test.android.playlistmaker.ui.search.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.domain.search.model.SearchTrackState
import gen.test.android.playlistmaker.domain.search.model.TrackSearch
import gen.test.android.playlistmaker.ui.player.activity.KEY_PLAYER_ACTIVITY
import gen.test.android.playlistmaker.ui.player.activity.PlayerActivity
import gen.test.android.playlistmaker.ui.search.view_model.SearchViewModel


private const val CLICK_DEBOUNCE_DELAY = 1000L


class SearchActivity : AppCompatActivity() {


    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private val debounceRunnable = Runnable { isClickAllowed = true }

    private var searchEdit: EditText? = null
    private var comProblemLL: LinearLayout? = null
    private var notFoundLL: LinearLayout? = null
    private var updateRequestBtn: Button? = null
    private var historySearchLL: LinearLayout? = null
    private var progressSearchLL: LinearLayout? = null
    private var tracksListLL: LinearLayout? = null
    private var clearHistoryBtn: Button? = null
    private val adapter = TrackSearchAdapter {
        if (clickDebounce()) {
            viewModel.historyAdd(it)
            startPlayerActivity(it)
        }
    }
    private val adapterHistory = TrackSearchAdapter { startPlayerActivity(it) }
    private lateinit var manager: InputMethodManager
    private lateinit var viewModel: SearchViewModel

    override fun onDestroy() {
        super.onDestroy()
        //handler.removeCallbacks(searchRunnable)
        handler.removeCallbacks(debounceRunnable)
    }



    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed(debounceRunnable, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun startPlayerActivity(track: TrackSearch) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(KEY_PLAYER_ACTIVITY, Gson().toJson(track))
        startActivity(intent)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun search() {
        manager.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
        viewModel.search(searchEdit?.text.toString())
    }

    private fun goneAll(view: LinearLayout?) {
        val views =
            listOf(tracksListLL, notFoundLL, comProblemLL, historySearchLL, progressSearchLL)
        views.forEach {
            when (it) {
                view -> it?.visibility = View.VISIBLE
                else -> it?.visibility = View.GONE
            }
        }
    }

    private fun viewHistory() {
        val lArr = viewModel.historyRead()
        //val lArr = searchHistory.read()
        if (lArr.isNotEmpty()) {
            adapterHistory.clearItems()//TODO("Remove")
            adapterHistory.setItems(lArr)//TODO("Remove")
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
        progressSearchLL = findViewById(R.id.progressLL)
        updateRequestBtn?.setOnClickListener { search() }
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
            adapter.clearItems()//TODO("Remove")
            adapter.notifyDataSetChanged()

            manager.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clear.visibility = clearButtonVisibility(s)
               // searchTxt = s.toString()
                if ((searchEdit?.hasFocus() == true) && (s?.isEmpty() == true)) {
                    viewHistory()
                }
                //else goneAll(null)
                viewModel.searchDebounce(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        searchEdit?.addTextChangedListener(simpleTextWatcher)
        searchEdit?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && (searchEdit!!.text.isEmpty()))
                viewHistory()
        }

        historySearchLL = findViewById(R.id.historySearchLL)
        clearHistoryBtn = findViewById(R.id.clearHistoryBtn)
        clearHistoryBtn?.setOnClickListener {
            viewModel.historyClear()
            //searchHistory.clearHistory()
            historySearchLL?.visibility = View.GONE
        }
    }

    private fun render(state: SearchTrackState) {
        when (state) {
            is SearchTrackState.Content -> showContent(state.movies)
            is SearchTrackState.Empty -> showEmpty()
            is SearchTrackState.Error -> showError()
            is SearchTrackState.Loading -> showLoading()
            is SearchTrackState.History -> showHistory(state.movies)
        }
    }

    private fun showLoading() {
        goneAll(progressSearchLL)
    }

    private fun showError() {
        goneAll(comProblemLL)
    }

    private fun showEmpty() {
        goneAll(notFoundLL)
    }

    private fun showContent(movies: ArrayList<TrackSearch>) {
        goneAll(tracksListLL)
        adapter.clearItems()//TODO("Remove")
        adapter.setItems(movies)//TODO("Remove")
        adapter.notifyDataSetChanged()
    }
    private fun showHistory(movies: Collection<TrackSearch>) {
        goneAll(historySearchLL)
        adapterHistory.clearItems()
        adapterHistory.setItems(movies as ArrayList<TrackSearch>)
        adapterHistory.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel = ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]
        setListeners()
       // searchHistory = SearchHistory((application as App).sharedPrefs)
        manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val recycler = findViewById<RecyclerView>(R.id.tracksList)
        recycler.layoutManager = LinearLayoutManager(this)
       // adapter.setItems(tracks)//TODO("Remove")
        recycler.adapter = adapter

        val recyclerHistory = findViewById<RecyclerView>(R.id.historySearchList)
        recyclerHistory.layoutManager = LinearLayoutManager(this)
       // adapterHistory.setItems(tracksHistory)//TODO("Remove")
        recyclerHistory.adapter = adapterHistory
        viewModel.observeState().observe(this) {
            render(it)
        }

    }


}

/*musicService.search(searchEdit?.text.toString())
.enqueue(object : Callback<TrackSearchResponse> {
    override fun onResponse(
        call: Call<TrackSearchResponse>,
        response: Response<TrackSearchResponse>,
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

    override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
        tracks.clear()
        adapter.notifyDataSetChanged()
        goneAll(comProblemLL)
    }
})*/

/*movies.map { Track(it.trackName,it.artistName,it.trackTimeMillis,it
    .artworkUrl100,it.trackId,it.collectionName,it.releaseDate,it.primaryGenreName,it
    .country,it.previewUrl) }*/

/*private var baseUrl = "https://itunes.apple.com"
private val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
  .build()
private val musicService = retrofit.create(ItunesAppleApi::class.java)*/
//private const val RESPONSE_OK = 200
//// goneAll(progressSearchLL)
//searchHistory.add(it)
// private val tracks = ArrayList<TrackSearch>()//TODO("Remove")
//  private val tracksHistory = ArrayList<TrackSearch>()//TODO("Remove")
/*
override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    */
/*outState.putString(KEY_DATA, searchTxt)*//*

}

override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    */
/*searchTxt = savedInstanceState.getString(KEY_DATA).toString()
    searchEdit?.setText(searchTxt)*//*

}*/
//private lateinit var searchHistory: SearchHistory
//private const val KEY_DATA = "info"