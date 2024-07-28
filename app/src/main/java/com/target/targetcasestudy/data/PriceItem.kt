package com.target.targetcasestudy.data

import com.google.gson.annotations.SerializedName

/**
 * Data class for providing regular and sale price info for a product item
 */
data class PriceItem(
    @SerializedName("amount_in_cents")
    val amountInCents: Int,
    @SerializedName("currency_symbol")
    val currencySymbol: String,
    @SerializedName("display_string")
    val displayString: String
)