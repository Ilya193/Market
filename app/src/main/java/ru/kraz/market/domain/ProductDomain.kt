package ru.kraz.market.domain

import ru.kraz.market.core.ToMapper
import ru.kraz.market.presentation.ProductUi

data class ProductDomain(
    private val id: Int,
    private val name: String,
    private val description: String,
    private val url: String
): ToMapper<ProductUi> {
    override fun map(): ProductUi = ProductUi.Base(id, name, description, url)
}