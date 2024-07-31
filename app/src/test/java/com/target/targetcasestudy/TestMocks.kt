package com.target.targetcasestudy

import com.target.targetcasestudy.data.Deal
import com.target.targetcasestudy.data.PriceItem

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
}