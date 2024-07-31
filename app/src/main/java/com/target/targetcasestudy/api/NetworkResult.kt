package com.target.targetcasestudy.api

/**
 * Sealed class that holds different states of the api call. Success, Error or Loading
 */
sealed class ApiResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ApiResult<T>(data)
    class Error<T>(message: String?) : ApiResult<T>(message = message)
    class Loading<T> : ApiResult<T>()
}