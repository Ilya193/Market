package ru.kraz.market.domain

import ru.kraz.market.core.EventWrapper
import ru.kraz.market.core.ToMapper
import ru.kraz.market.presentation.ReviewUi
import ru.kraz.market.presentation.ReviewsUi
import java.net.UnknownHostException

sealed class ReviewsDomain : ToMapper<ReviewsUi> {

    data class Base(
        private val list: List<ReviewDomain>
    ): ReviewsDomain() {
        override fun map(): ReviewsUi = ReviewsUi.Base(EventWrapper.State(list.map { it.map() }))
    }

    data class Fail(
        private val e: Exception
    ): ReviewsDomain() {
        override fun map(): ReviewsUi {
            if (e is UnknownHostException) return ReviewsUi.Base(EventWrapper.State(listOf(ReviewUi.Fail("No connection"))))
            return ReviewsUi.Base(EventWrapper.State(listOf(ReviewUi.Fail("Error has occurred"))))
        }
    }
}