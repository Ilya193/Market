package ru.kraz.market.domain

interface ProductsRepository {
    suspend fun fetchProducts(): Result<List<ProductDomain>>
    suspend fun fetchReviews(productId: Int): Result<List<ReviewDomain>>
    suspend fun sendReview(textReview: String, productId: Int): Result<List<ReviewDomain>>

    suspend fun <T> handleExceptions(block: suspend () -> Result<T>): Result<T>

}