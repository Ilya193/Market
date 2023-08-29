package ru.kraz.market.presentation

import ru.kraz.market.core.Communication
import ru.kraz.market.core.EventWrapper

sealed class ReviewsUi {
    abstract fun map(communication: Communication<EventWrapper<List<ReviewUi>>>)

    data class Base(
        private val list: EventWrapper.State<List<ReviewUi>>
    ): ReviewsUi() {
        override fun map(communication: Communication<EventWrapper<List<ReviewUi>>>) {
            communication.map(list)
        }
    }
}