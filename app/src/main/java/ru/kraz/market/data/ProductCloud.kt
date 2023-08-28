package ru.kraz.market.data

import com.google.gson.annotations.SerializedName
import ru.kraz.market.core.ToMapper

data class ProductCloud(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("name")
    private val name: String,
    @SerializedName("description")
    private val description: String,
    @SerializedName("url")
    private val url: String
): ToMapper<ProductData> {
    override fun map(): ProductData = ProductData(id, name, description, url)
}
