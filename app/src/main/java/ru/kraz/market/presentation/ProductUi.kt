package ru.kraz.market.presentation

import ru.kraz.market.core.Comparing

sealed class ProductUi(
    open val id: Int = -1,
    open val name: String,
    open val description: String = "",
    open val url: String = ""
): Comparing<ProductUi> {

    override fun same(item: ProductUi): Boolean = false
    override fun sameContent(item: ProductUi): Boolean = false

    data class Base(
        override val id: Int,
        override val name: String,
        override val description: String,
        override val url: String
    ): ProductUi(id, name, description, url) {
        override fun same(item: ProductUi): Boolean = item is Base && id == item.id
        override fun sameContent(item: ProductUi): Boolean = this == item
    }
}

sealed class ProductUiState {
    data class Success(val data: List<ProductUi>) : ProductUiState()

    data class Error(val message: String) : ProductUiState()

    object Loading : ProductUiState()
}