package ru.kraz.market.data

import ru.kraz.market.core.ToMapper
import ru.kraz.market.domain.ReviewsDomain

sealed class ReviewsData : ToMapper<ReviewsDomain> {

    data class Base(
        private val list: List<ReviewData>
    ): ReviewsData() {
        override fun map(): ReviewsDomain = ReviewsDomain.Base(list.map { it.map() })
    }

    data class Fail(
        private val e: Exception
    ): ReviewsData() {
        override fun map(): ReviewsDomain = ReviewsDomain.Fail(e)
    }
}