package com.dileep.giphysampletest.api

import com.dileep.giphysampletest.data.source.remote.RemoteTrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GippyApiService {

    @GET(SUB_PATH_TREND)
    suspend fun requestTrendingGiphy(
        @Query("api_key") key: String = API_KEY,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en"
    ): RemoteTrendingResponse

    @GET(SUB_PATH_SEARCH)
    suspend fun requestToSearchGiphy(
        @Query("api_key") key: String = API_KEY,
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = "g",
        @Query("lang") lang: String = "en"
    ): RemoteTrendingResponse

    companion object {
        const val API_KEY = "3f3sLhmn9AiKJbe4ZgFKH2Rfy2p07TKp"
        const val SUB_PATH_TREND = "trending"
        const val SUB_PATH_SEARCH = "search"
        const val TRENDING_STARTING_PAGE_INDEX = 0
    }
}