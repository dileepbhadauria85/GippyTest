package com.dileep.giphysampletest.data.source.local

import androidx.paging.PagingSource
import com.dileep.giphysampletest.data.source.local.dao.GifDao
import com.dileep.giphysampletest.data.source.local.dao.RemoteKeysDao
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.data.source.local.entity.RemoteKey
import javax.inject.Inject

class LocalDataSourceImpl  @Inject constructor(
    private val gifDao: GifDao,
    private val remoteKeysDao: RemoteKeysDao
): LocalDataSource {
    override suspend fun saveGifs(gifs: List<Gif>) {
        gifDao.insertGifs(gifs)
    }

    override fun getGifsTrending(): PagingSource<Int, Gif> {
        return gifDao.selectGifs()
    }

    override fun getFavoriteGifs(): PagingSource<Int, Gif> {
        return gifDao.selectFavoriteGifs()
    }

    override suspend fun updateGif(gif: Gif): Int {
        return gifDao.updateGif(gif)
    }

    override suspend fun clearGifs() {
        gifDao.clearGifs()
    }

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>) {
        remoteKeysDao.insertRemoteKeys(remoteKeys)
    }

    override suspend fun getRemoteKeyWithGifId(gifId: String): RemoteKey? {
        return remoteKeysDao.selectRemoteKeyGifId(gifId = gifId)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }
}