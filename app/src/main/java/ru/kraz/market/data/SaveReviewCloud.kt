package ru.kraz.market.data

import com.google.gson.annotations.SerializedName

data class SaveReviewCloud(
    @SerializedName("title")
    val textReview: String,
    val productId: Int
)