package com.dileep.giphysampletest.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.dileep.giphysampletest.R
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.databinding.TrendingFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class TrendingFragment : BaseFragment(), TrendingGifToggleListener {


    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: TrendingFragmentBinding
    private lateinit var adapter: TrendingGifAdapter
    private var trendingJob: Job? = null
    private var _listener: TrendingGifToggleListener? = null
    private val listener get() = _listener!!
    private var searchJob: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.trending_fragment, container, false)
        binding.searchVm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        _listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initTrending()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        initSearch(query)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.etSearch.text.trim().toString())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _listener = null
    }

    private fun initAdapter() {
        adapter = TrendingGifAdapter(listener)
        binding.rvTrending.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GifLoadStateAdapter(adapter::retry),
            footer = GifLoadStateAdapter(adapter::retry)
        )
        binding.rvSearch.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GifLoadStateAdapter(adapter::retry),
            footer = GifLoadStateAdapter(adapter::retry)
        )
    }

    private fun initTrending() {
        trendingJob?.cancel()
        trendingJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getTrendingGif().collectLatest {
                binding.rvTrending.visibility = View.VISIBLE
                binding.rvSearch.visibility = View.GONE
                adapter.submitData(it)
            }
        }
    }

    override fun onToggleFavorite(gif: Gif) {
        gif.isFavorite = !gif.isFavorite
        viewModel.saveFavoriteGif(gif)
    }

    private fun updateGiphyListFromInput() {
        binding.etSearch.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }
    private fun initSearch(query: String) {
        binding.etSearch.run {
            this.setText(query)

            this.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    updateGiphyListFromInput()
                    true
                } else {
                    false
                }
            }
            this.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    updateGiphyListFromInput()
                    true
                } else {
                    false
                }
            }

        }

    }
    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchGiphy(query).collectLatest {
                binding.rvTrending.visibility = View.GONE
                binding.rvSearch.visibility = View.VISIBLE
                adapter.submitData(it)
            }
        }
    }

    companion object {
        fun newInstance() = TrendingFragment()
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Giphy"
    }
}
