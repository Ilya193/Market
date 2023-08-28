package ru.kraz.market.presentation

import ru.kraz.market.core.Communication

sealed class ProductsUi {
    abstract fun map(communication: Communication<List<ProductUi>>)

    data class Base(
        private val list: List<ProductUi>
    ): ProductsUi() {
        override fun map(communication: Communication<List<ProductUi>>) {
            communication.map(list)
        }
    }
}