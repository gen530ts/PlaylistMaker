package gen.test.android.playlistmaker.ui.search.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import gen.test.android.playlistmaker.R
import gen.test.android.playlistmaker.databinding.FragmentSearchBinding
import gen.test.android.playlistmaker.domain.search.model.SearchTrackState
import gen.test.android.playlistmaker.domain.search.model.TrackSearch
import gen.test.android.playlistmaker.ui.player.activity.KEY_PLAYER_ACTIVITY
import gen.test.android.playlistmaker.ui.search.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val CLICK_DEBOUNCE_DELAY = 1000L

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater,
            container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

        manager = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as
                InputMethodManager

        val recycler = binding.tracksList
        recycler.layoutManager = LinearLayoutManager(requireContext())

        recycler.adapter = adapter

        val recyclerHistory = binding.historySearchList
        recyclerHistory.layoutManager = LinearLayoutManager(requireContext())

        recyclerHistory.adapter = adapterHistory
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }


    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private val debounceRunnable = Runnable { isClickAllowed = true }

    private lateinit var searchEdit: EditText
    private lateinit var comProblemLL: LinearLayout
    private lateinit var notFoundLL: LinearLayout
    private lateinit var updateRequestBtn: Button
    private lateinit var historySearchLL: LinearLayout
    private lateinit var progressSearchLL: LinearLayout
    private lateinit var tracksListLL: LinearLayout
    private lateinit var clearHistoryBtn: Button
    private val adapter = TrackSearchAdapter {
        if (clickDebounce()) {
            viewModel.historyAdd(it)
            startPlayerActivity(it)
        }
    }
    private val adapterHistory = TrackSearchAdapter { startPlayerActivity(it) }
    private lateinit var manager: InputMethodManager

    private val viewModel by viewModel<SearchViewModel>()

    override fun onDestroy() {
        super.onDestroy()

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


        findNavController().navigate(
            R.id.action_searchFragment_to_playerActivity,
            bundleOf(KEY_PLAYER_ACTIVITY to Gson().toJson(track)))

    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            GONE
        } else {
            View.VISIBLE
        }
    }

    private fun search() {
        manager.hideSoftInputFromWindow(requireActivity().window.currentFocus!!.windowToken, 0)
        viewModel.search(searchEdit.text.toString())
    }

    private fun goneAll(view: LinearLayout?) {
        val views =
            listOf(tracksListLL, notFoundLL, comProblemLL, historySearchLL, progressSearchLL)
        views.forEach {
            when (it) {
                view -> it.visibility = View.VISIBLE
                else -> it.visibility = GONE
            }
        }
    }

    private fun viewHistory() {
        val lArr = viewModel.historyRead()

        if (lArr.isNotEmpty()) {
            adapterHistory.clearItems()
            adapterHistory.setItems(lArr)
            adapterHistory.notifyDataSetChanged()
            goneAll(historySearchLL)
        }
    }

    private fun setListeners() {
        val clear = binding.clearImageView
        searchEdit = binding.searchEditText
        notFoundLL = binding.notFoundLL
        comProblemLL = binding.comProblemLL
        tracksListLL = binding.tracksListLL
        updateRequestBtn = binding.updateRequestBtn
        progressSearchLL = binding.progressLL
        updateRequestBtn.setOnClickListener { search() }
        searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()

            }
            false
        }



        clear.visibility = GONE
        clear.setOnClickListener {
            searchEdit.text?.clear()
            adapter.clearItems()
            adapter.notifyDataSetChanged()

            manager.hideSoftInputFromWindow(requireActivity().window.currentFocus!!.windowToken, 0)
        }

        searchEdit.addTextChangedListener(
            onTextChanged = { charSequence,_,_,_-> clear.visibility = clearButtonVisibility(charSequence)

                if (searchEdit.hasFocus() && (charSequence?.isEmpty() == true)) {
                    viewHistory()
                }

                viewModel.searchDebounce(charSequence?.toString() ?: "")}
        )
        searchEdit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && (searchEdit.text.isEmpty()))
                viewHistory()
        }

        historySearchLL = binding.historySearchLL
        clearHistoryBtn = binding.clearHistoryBtn
        clearHistoryBtn.setOnClickListener {
            viewModel.historyClear()

            historySearchLL.visibility = GONE
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
        adapter.clearItems()
        adapter.setItems(movies)
        adapter.notifyDataSetChanged()
    }
    private fun showHistory(movies: Collection<TrackSearch>) {
        goneAll(historySearchLL)
        adapterHistory.clearItems()
        adapterHistory.setItems(movies as ArrayList<TrackSearch>)
        adapterHistory.notifyDataSetChanged()
    }
}