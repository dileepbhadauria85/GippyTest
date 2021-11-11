package com.dileep.giphysampletest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.dileep.giphysampletest.R
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.databinding.FavoritesFragmentBinding
import com.dileep.giphysampletest.utils.setGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(), TrendingGifToggleListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var fAdapter: FavoriteGifAdapter
    private var favoriteJob: Job? = null
    private var _listener: TrendingGifToggleListener? = null
    private val listener get() = _listener!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.favorites_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        _listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _listener = null
    }

    private fun initAdapter() {
        fAdapter = FavoriteGifAdapter(listener)
        binding.rvFavorites.adapter = fAdapter
        binding.rvFavorites.apply {
            setGridLayoutManager(2)
            this.adapter = fAdapter
        }
    }

    private fun initFavorite() {
        favoriteJob?.cancel()
        favoriteJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavoriteGif().collectLatest {
                fAdapter.submitData(it)
            }
        }
        viewModel.update.observe(viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                }
            })
    }

    override fun onToggleFavorite(gif: Gif) {
        gif.isFavorite = !gif.isFavorite
        viewModel.saveFavoriteGif(gif)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}