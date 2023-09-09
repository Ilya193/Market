package ru.kraz.market.presentation

import ru.kraz.market.domain.ProductDomain
import ru.kraz.market.core.Mapper

class BaseToProductUiMapper : Mapper<ProductDomain, ProductUi.Base> {
    override fun map(data: ProductDomain): ProductUi.Base =
        ProductUi.Base(data.id, data.name, data.description, data.url)
}