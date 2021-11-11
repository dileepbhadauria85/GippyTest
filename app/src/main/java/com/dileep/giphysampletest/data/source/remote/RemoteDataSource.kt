package com.dileep.giphysampletest.data.source.remote

interface RemoteDataSource {
    suspend fun getTrendingGiphy(itemsPerPage: Int, offset: Int): RemoteTrendingResponse
    suspend fun getSearchedGiphy(query: String,itemsPerPage: Int, offset: Int): RemoteTrendingResponse
}