package com.dileep.giphysampletest.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dileep.giphysampletest.api.GippyApiService
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.data.source.remote.RemoteDataSource
import com.dileep.giphysampletest.utils.mapToGif
import retrofit2.HttpException
import java.io.IOException


class SearchPagingSource(private val remoteDataSource: RemoteDataSource,
                         private val query: String) : PagingSource<Int, Gif>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        val apiQuery = query
        val pageSize = params.loadSize
        val position = params.key ?: GippyApiService.TRENDING_STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.getSearchedGiphy(query = apiQuery, itemsPerPage = pageSize, offset = position * pageSize)
            val gifs = response.mapToGif()
            LoadResult.Page(
                data = gifs,
                prevKey = if (position == GippyApiService.TRENDING_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (gifs.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Gif>): Int? {
        TODO("Not yet implemented")
    }
}