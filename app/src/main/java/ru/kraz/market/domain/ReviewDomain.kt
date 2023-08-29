package ru.kraz.market.domain

import ru.kraz.market.core.ToMapper
import ru.kraz.market.presentation.ProductUi
import ru.kraz.market.presentation.ReviewUi

data class ReviewDomain(
    private val id: Int,
    private val textReview: String,
    private val productId: Int,
): ToMapper<ReviewUi> {
    override fun map(): ReviewUi = ReviewUi.Base(id, textReview, productId)
}