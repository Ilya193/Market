package ru.kraz.market.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProductRepository {
    suspend fun fetchProducts(): Flow<ProductsData>
    suspend fun fetchReviews(productId: Int): Flow<ReviewsData>
    suspend fun sendReview(textReview: String, productId: Int): Flow<ReviewsData>

    class Base(
        private val productCloudDataSource: ProductCloudDataSource
    ) : ProductRepository {
        override suspend fun fetchProducts(): Flow<ProductsData> = flow {
            try {
                val data = productCloudDataSource.fetchProducts()
                emit(ProductsData.Base(data.map { it.map() }))
            } catch (e: Exception) {
                emit(ProductsData.Fail(e))
            }
        }

        override suspend fun fetchReviews(productId: Int): Flow<ReviewsData> = flow {
            try {
                val data = productCloudDataSource.fetchReviews(productId)
                emit(ReviewsData.Base(data.map { it.map() }))
            } catch (e: Exception) {
                emit(ReviewsData.Fail(e))
            }
        }

        override suspend fun sendReview(textReview: String, productId: Int): Flow<ReviewsData> = flow {
            try {
                val data = productCloudDataSource.sendReview(textReview, productId)
                emit(ReviewsData.Base(data.map { it.map() }))
            } catch (e: Exception) {
                emit(ReviewsData.Fail(e))
            }
        }
    }
}