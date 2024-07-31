package com.target.targetcasestudy

import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.PriceItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/**
 * Provides Mock objects for testing
 */
object TestMocks {
    fun provideMockDeal() = Deal(
        aisle = "g34",
        availability = "In stock",
        description = "description",
        id = 1,
        imageUrl = "imageUrl",
        regularPrice = PriceItem(10, "$", "$10.0"),
        salePrice = PriceItem(5, "$", "$5.0"),
        title = "title",
        fulfillment = "Online"
    )

    fun provideMockDealWithoutSalePrice() = provideMockDeal().copy(
        salePrice = null
    )

    fun <T> provideErrorResponse(): Response<T> = Response.error<T>(
        500,
        "{}".toResponseBody("application/json".toMediaTypeOrNull())
    )
}