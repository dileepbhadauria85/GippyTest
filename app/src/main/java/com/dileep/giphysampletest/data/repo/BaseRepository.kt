package com.dileep.giphysampletest.data.repo

import com.dileep.giphysampletest.data.ResWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResWrapper.Success(apiCall.invoke())
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        ResWrapper.Error(isNetworkError = false, errorCode = throwable.code(), errorBody = throwable.response()?.errorBody())
                    }
                    else -> {
                        ResWrapper.Error(true, null, null)
                    }
                }
            }

        }
    }
}