package com.dileep.giphysampletest.data

import okhttp3.ResponseBody


sealed class ResWrapper<out T> {
    data class Success<out T>(val value: T) : ResWrapper<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ResWrapper<Nothing>()

    object Loading : ResWrapper<Nothing>()
}