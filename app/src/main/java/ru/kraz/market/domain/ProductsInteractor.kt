package ru.kraz.market.domain

interface ProductsInteractor {
    suspend fun fetchProduct(): Result<List<ProductDomain>>
    suspend fun fetchReviews(productId: Int): Result<List<ReviewDomain>>
    suspend fun sendReview(textReview: String, productId: Int): Result<List<ReviewDomain>>

    class Base(
        private val productsRepository: ProductsRepository
    ) : ProductsInteractor {
        override suspend fun fetchProduct(): Result<List<ProductDomain>> {
            return productsRepository.fetchProducts()
        }

        override suspend fun fetchReviews(productId: Int): Result<List<ReviewDomain>> {
            return productsRepository.fetchReviews(productId)
        }

        override suspend fun sendReview(textReview: String, productId: Int): Result<List<ReviewDomain>> {
            return productsRepository.sendReview(textReview, productId)
        }
    }

}