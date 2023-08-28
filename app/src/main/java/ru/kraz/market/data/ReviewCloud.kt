package ru.kraz.market.data

import com.google.gson.annotations.SerializedName
import ru.kraz.market.core.ToMapper

data class ReviewCloud(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("title")
    private val textReview: String,
    @SerializedName("productId")
    private val productId: Int,
): ToMapper<ReviewData> {
    override fun map(): ReviewData = ReviewData(id, textReview, productId)
}
