package ru.kraz.market.presentation

import ru.kraz.market.core.Comparing
import ru.kraz.market.core.EventWrapper

sealed class ReviewUi(
    open val id: Int = -1,
    open val textReview: String,
    open val productId: Int = -1,
): Comparing<ReviewUi> {
    override fun same(item: ReviewUi): Boolean = false
    override fun sameContent(item: ReviewUi): Boolean = false

    data class Base(
        override val id: Int,
        override val textReview: String,
        override val productId: Int,
    ): ReviewUi(id, textReview, productId) {
        override fun same(item: ReviewUi): Boolean = item is Base && id == item.id
        override fun sameContent(item: ReviewUi): Boolean = this == item
    }

    data class Fail(val e: String) : ReviewUi(textReview = e)
}