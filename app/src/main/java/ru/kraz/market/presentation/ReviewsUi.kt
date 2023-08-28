package ru.kraz.market.presentation

import ru.kraz.market.core.Communication

sealed class ReviewsUi {
    abstract fun map(communication: Communication<List<ReviewUi>>)

    data class Base(
        private val list: List<ReviewUi>
    ): ReviewsUi() {
        override fun map(communication: Communication<List<ReviewUi>>) {
            communication.map(list)
        }
    }
}