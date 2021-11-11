package com.dileep.giphysampletest.data.source.local

import androidx.paging.PagingSource
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.data.source.local.entity.RemoteKey

interface LocalDataSource {
    suspend fun saveGifs(gifs: List<Gif>)
    fun getGifsTrending(): PagingSource<Int, Gif>
    fun getFavoriteGifs(): PagingSource<Int, Gif>
    suspend fun updateGif(gif: Gif): Int
    suspend fun clearGifs()

    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>)
    suspend fun getRemoteKeyWithGifId(gifId: String): RemoteKey?
    suspend fun clearRemoteKeys()
}