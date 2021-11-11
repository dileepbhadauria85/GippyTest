package com.dileep.giphysampletest.data.repo

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dileep.giphysampletest.data.ResWrapper
import com.dileep.giphysampletest.data.paging.GifRemoteMediator
import com.dileep.giphysampletest.data.paging.SearchPagingSource
import com.dileep.giphysampletest.data.source.local.LocalDataSource
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.data.source.local.entity.RemoteKey
import com.dileep.giphysampletest.data.source.remote.RemoteDataSource
import com.dileep.giphysampletest.data.source.remote.RemoteTrendingResponse
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.Constructor
import javax.inject.Inject
@OptIn(ExperimentalPagingApi::class)
class GiphyRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource,
                                              private val localDataSource: LocalDataSource
): BaseRepository(),GiphyRepository {
    override fun getTrendingGifStream(): Flow<PagingData<Gif>> {
        val pagingSourceFactory = { localDataSource.getGifsTrending() }
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = GifRemoteMediator(remoteDataSource, localDataSource,""),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    override fun getSearchResultStream(query: String): Flow<PagingData<Gif>> {
        Log.d("GiphyRepositoryImpl", "New query: $query")
      /*  val pagingSourceFactory = { localDataSource.getGifsTrending() }
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = GifRemoteMediator(remoteDataSource, localDataSource, query),
            pagingSourceFactory = pagingSourceFactory
        ).flow*/
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(remoteDataSource, query) }
        ).flow
    }

    override fun getFavoriteGif(): Flow<PagingData<Gif>> {
        val pagingSourceFactory = { localDataSource.getFavoriteGifs() }
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun saveTrendingGifs(gifs: List<Gif>) {
        localDataSource.saveGifs(gifs)
    }

    override suspend fun updateFavoriteGif(gif: Gif): Int {
        return localDataSource.updateGif(gif)
    }

    override suspend fun clearGifs() {
        localDataSource.clearGifs()
    }

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>) {
        localDataSource.saveRemoteKeys(remoteKeys)
    }

    override suspend fun getRemoteKeyWithGifId(gifId: String): RemoteKey? {
        return localDataSource.getRemoteKeyWithGifId(gifId)
    }

    override suspend fun clearRemoteKeys() {
        localDataSource.clearRemoteKeys()
    }



    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }
}