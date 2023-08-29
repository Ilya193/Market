package ru.kraz.market.data

import ru.kraz.market.core.ToMapper
import ru.kraz.market.domain.ProductsDomain

sealed class ProductsData : ToMapper<ProductsDomain> {

    data class Base(
        private val list: List<ProductData>
    ): ProductsData() {
        override fun map(): ProductsDomain = ProductsDomain.Base(list.map { it.map() })
    }

    data class Fail(
        private val e: Exception
    ): ProductsData() {
        override fun map(): ProductsDomain = ProductsDomain.Fail(e)
    }
}
