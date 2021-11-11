package com.dileep.giphysampletest.data.repo

import androidx.paging.PagingData
import com.dileep.giphysampletest.data.ResWrapper
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.data.source.local.entity.RemoteKey
import com.dileep.giphysampletest.data.source.remote.RemoteTrendingResponse
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    fun getTrendingGifStream(): Flow<PagingData<Gif>>
    suspend fun saveTrendingGifs(gifs: List<Gif>)
    fun getFavoriteGif(): Flow<PagingData<Gif>>
    suspend fun updateFavoriteGif(gif: Gif): Int
    suspend fun clearGifs()

    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>)
    suspend fun getRemoteKeyWithGifId(gifId: String): RemoteKey?
    suspend fun clearRemoteKeys()
    //suspend fun requestToSearchGiphy(q: String, limit: Int, offset: Int): ResWrapper<RemoteTrendingResponse>
     fun getSearchResultStream(query: String): Flow<PagingData<Gif>>
}