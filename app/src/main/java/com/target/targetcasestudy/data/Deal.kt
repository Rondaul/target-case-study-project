package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a single product. Contains all the necessary info regarding the product.
 */
data class Deal(
    @SerializedName("aisle")
    val aisle: String,
    @SerializedName("availability")
    val availability: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("fulfillment")
    val fulfillment: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("regular_price")
    val priceItem: PriceItem,
    @SerializedName("sale_price")
    val salePrice: PriceItem,
    @SerializedName("title")
    val title: String
)