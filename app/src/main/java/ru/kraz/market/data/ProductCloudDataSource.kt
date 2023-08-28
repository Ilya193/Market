package ru.kraz.market.data

interface ProductCloudDataSource {
    suspend fun fetchProducts(): List<ProductCloud>
    suspend fun sendReview(textReview: String, productId: Int): List<ReviewCloud>

    class Base(
        private val productService: ProductService
    ): ProductCloudDataSource {
        override suspend fun fetchProducts(): List<ProductCloud> {
            return productService.fetchProducts()
        }

        override suspend fun sendReview(textReview: String, productId: Int): List<ReviewCloud> {

            return productService.sendReview(SaveReviewCloud(textReview, productId))
        }
    }
}