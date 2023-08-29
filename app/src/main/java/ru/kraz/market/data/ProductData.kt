package ru.kraz.market.data

import ru.kraz.market.core.ToMapper
import ru.kraz.market.domain.ProductDomain

data class ProductData(
    private val id: Int,
    private val name: String,
    private val description: String,
    private val url: String
): ToMapper<ProductDomain> {
    override fun map(): ProductDomain = ProductDomain(id, name, description, url)
}