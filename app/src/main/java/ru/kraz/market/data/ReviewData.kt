package ru.kraz.market.data

import ru.kraz.market.core.ToMapper
import ru.kraz.market.domain.ReviewDomain

data class ReviewData(
    private val id: Int,
    private val textReview: String,
    private val productId: Int,
): ToMapper<ReviewDomain> {
    override fun map(): ReviewDomain = ReviewDomain(id, textReview, productId)
}