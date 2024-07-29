package com.target.targetcasestudy.api

import retrofit2.Response

/**
 * Base abstract class to convert api response to [ApiResult] response
 */
abstract class BaseApiResponse {

    /**
     * Takes a lambda that returns response object and converts and return the data as [ApiResult]
     */
    suspend fun <T> makeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ApiResult.Success(body)
                }
            }
            return error("Code: ${response.code()}, Message: ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): ApiResult<T> =
        ApiResult.Error("Api call failed $errorMessage")
}