package ru.kraz.market.domain

import ru.kraz.market.core.ToMapper
import ru.kraz.market.presentation.ProductUi

data class ProductDomain(
    val id: Int,
    val name: String,
    val description: String,
    val url: String
): ToMapper<ProductUi> {
    override fun map(): ProductUi = ProductUi.Base(id, name, description, url)
}