package com.dileep.giphysampletest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dileep.giphysampletest.data.repo.GiphyRepository
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private var currentTrendingGif: Flow<PagingData<Gif>>? = null
    private var currentFavoriteGif: Flow<PagingData<Gif>>? = null
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Gif>>? = null
    private var _update = MutableLiveData<Event<Boolean>>()
    val update: LiveData<Event<Boolean>>
        get() = _update
    val queryString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getTrendingGif(): Flow<PagingData<Gif>> {
        val lastResponse = currentTrendingGif
        if (lastResponse != null) return lastResponse
        val newResponse = fetchTrendingGif()
        currentTrendingGif = newResponse
        return newResponse
    }

    private fun fetchTrendingGif() = repository.getTrendingGifStream()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)


    fun searchGiphy(query: String): Flow<PagingData<Gif>> {
        val lastResponse = currentTrendingGif
        if(query == currentQueryValue && lastResponse != null)return lastResponse
        val newResponse = searchGif(query)
        currentTrendingGif = newResponse
        return newResponse
    }

    private fun searchGif(query: String) = repository.getSearchResultStream(query)
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    fun saveFavoriteGif(gif: Gif) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.updateFavoriteGif(gif) == 1){
                Log.d(TAG, "UPDATE SUCCESS")
                _update.postValue(Event(true))
            } else {
                Log.d(TAG, "UPDATE FAILURE")
            }
        }
    }




    fun getFavoriteGif(): Flow<PagingData<Gif>> {
        val lastResponse = currentFavoriteGif
        if (lastResponse != null) return lastResponse
        val newResponse = fetchFavoriteGif()
        currentFavoriteGif = newResponse
        return newResponse
    }

    private fun fetchFavoriteGif() = repository.getFavoriteGif()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }
}