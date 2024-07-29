package com.target.targetcasestudy.api

import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

/**
 * Retrofit Interface for api calls
 */
interface DealApi {
  @GET("deals")
  suspend fun retrieveDeals(): Response<Products>

  @GET("deals/{id}")
  suspend fun retrieveDeal(@Path("id") dealId: Int): Response<Deal>
}
