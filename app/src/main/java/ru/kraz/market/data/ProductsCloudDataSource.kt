package ru.kraz.market.data

interface ProductsCloudDataSource {
    suspend fun fetchProducts(): List<ProductCloud>
    suspend fun fetchReviews(productId: Int): List<ReviewCloud>
    suspend fun sendReview(textReview: String, productId: Int): List<ReviewCloud>

    class Base(
        private val productsService: ProductsService
    ): ProductsCloudDataSource {
        override suspend fun fetchProducts(): List<ProductCloud> {
            return productsService.fetchProducts()
        }

        override suspend fun fetchReviews(productId: Int): List<ReviewCloud> {
            return productsService.fetchReviews(productId)
        }

        override suspend fun sendReview(textReview: String, productId: Int): List<ReviewCloud> {
            return productsService.sendReview(SaveReviewCloud(textReview, productId))
        }
    }
}