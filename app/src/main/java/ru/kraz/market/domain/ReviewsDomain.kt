package ru.kraz.market.domain

import ru.kraz.market.core.ToMapper
import ru.kraz.market.presentation.ProductUi
import ru.kraz.market.presentation.ProductsUi
import ru.kraz.market.presentation.ReviewUi
import ru.kraz.market.presentation.ReviewsUi
import java.net.UnknownHostException

sealed class ReviewsDomain : ToMapper<ReviewsUi> {

    data class Base(
        private val list: List<ReviewDomain>
    ): ReviewsDomain() {
        override fun map(): ReviewsUi = ReviewsUi.Base(list.map { it.map() })
    }

    data class Fail(
        private val e: Exception
    ): ReviewsDomain() {
        override fun map(): ReviewsUi {
            if (e is UnknownHostException) return ReviewsUi.Base(listOf(ReviewUi.Fail("No connection")))
            return ReviewsUi.Base(listOf(ReviewUi.Fail("Error has occurred")))
        }
    }
}