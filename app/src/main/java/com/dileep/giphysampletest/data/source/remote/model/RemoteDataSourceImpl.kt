package com.dileep.giphysampletest.data.source.remote.model

import com.dileep.giphysampletest.api.GippyApiService
import com.dileep.giphysampletest.data.source.remote.RemoteDataSource
import com.dileep.giphysampletest.data.source.remote.RemoteTrendingResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val service: GippyApiService
): RemoteDataSource {
    override suspend fun getTrendingGiphy(itemsPerPage: Int, offset: Int): RemoteTrendingResponse {
        return service.requestTrendingGiphy(limit = itemsPerPage, offset = offset)
    }

    override suspend fun getSearchedGiphy(
        query: String,
        itemsPerPage: Int,
        offset: Int
    ): RemoteTrendingResponse {
        return service.requestToSearchGiphy(q = query, limit = itemsPerPage, offset = offset)
    }
}