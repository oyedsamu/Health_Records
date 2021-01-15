package com.decadevs.healthrecords.api

import okhttp3.ResponseBody
import retrofit2.Response

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure
        (
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: Response<Any>?

    ) : Resource<Nothing>()

}