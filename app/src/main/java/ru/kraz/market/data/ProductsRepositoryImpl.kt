package ru.kraz.market.data

import ru.kraz.market.domain.ProductDomain
import ru.kraz.market.domain.ProductsRepository
import ru.kraz.market.domain.Result
import ru.kraz.market.domain.ReviewDomain

class ProductsRepositoryImpl(
    private val productsCloudDataSource: ProductsCloudDataSource
) : ProductsRepository {
    override suspend fun fetchProducts(): Result<List<ProductDomain>> {
        return try {
            val data = productsCloudDataSource.fetchProducts().map { it.map() }
            Result.Success(data.map { it.map() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchReviews(productId: Int): Result<List<ReviewDomain>> {
        return try {
            val data = productsCloudDataSource.fetchReviews(productId).map { it.map() }
            Result.Success(data.map { it.map() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun sendReview(textReview: String, productId: Int): Result<List<ReviewDomain>> {
        return try {
            val data = productsCloudDataSource.sendReview(textReview, productId).map {it.map()}
            Result.Success(data.map { it.map() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}