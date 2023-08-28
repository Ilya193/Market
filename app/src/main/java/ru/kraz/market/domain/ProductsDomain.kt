package ru.kraz.market.domain

import ru.kraz.market.core.ToMapper
import ru.kraz.market.presentation.ProductUi
import ru.kraz.market.presentation.ProductsUi
import java.net.UnknownHostException

sealed class ProductsDomain: ToMapper<ProductsUi> {

    data class Base(
        private val list: List<ProductDomain>
    ): ProductsDomain() {
        override fun map(): ProductsUi = ProductsUi.Base(list.map { it.map() })
    }

    data class Fail(
        private val e: Exception
    ): ProductsDomain() {
        override fun map(): ProductsUi {
            if (e is UnknownHostException) return ProductsUi.Base(listOf(ProductUi.Fail("No connection")))
            return ProductsUi.Base(listOf(ProductUi.Fail("Error has occurred")))
        }
    }
}