package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

/**
 * Data class that holds list of products
 */
data class Products(
    @SerializedName("products")
    val products: List<Product>
)