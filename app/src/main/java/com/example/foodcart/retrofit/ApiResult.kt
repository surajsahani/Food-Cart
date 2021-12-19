package com.example.foodblogs.retrofit

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data : T) : ApiResult<T>()
    data class NoData(val data : String = "") : ApiResult<String>()
    data class Failure(val exception : Exception) : ApiResult<Exception>()
    object InProgress : ApiResult<Nothing>()
}