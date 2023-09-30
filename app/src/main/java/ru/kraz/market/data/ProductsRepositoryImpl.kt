package ru.kraz.market.data

import retrofit2.HttpException
import ru.kraz.market.domain.ErrorType
import ru.kraz.market.domain.ProductDomain
import ru.kraz.market.domain.ProductsRepository
import ru.kraz.market.domain.Result
import ru.kraz.market.domain.ReviewDomain
import java.net.UnknownHostException

class ProductsRepositoryImpl(
    private val productsCloudDataSource: ProductsCloudDataSource
) : ProductsRepository {
    override suspend fun fetchProducts(): Result<List<ProductDomain>> {
        return handleExceptions {
            val data = productsCloudDataSource.fetchProducts().map { it.map() }
            Result.Success(data.map { it.map() })
        }
    }

    override suspend fun fetchReviews(productId: Int): Result<List<ReviewDomain>> {
        return handleExceptions {
            val data = productsCloudDataSource.fetchReviews(productId).map { it.map() }
            Result.Success(data.map { it.map() })
        }
    }

    override suspend fun sendReview(textReview: String, productId: Int): Result<List<ReviewDomain>> {
        return handleExceptions {
            val data = productsCloudDataSource.sendReview(textReview, productId).map {it.map()}
            Result.Success(data.map { it.map() })
        }
    }

    override suspend fun <T> handleExceptions(block: suspend () -> Result<T>): Result<T> {
        return try {
            block()
        } catch (e: UnknownHostException) {
            Result.Error(ErrorType.NO_CONNECTION)
        } catch (e: HttpException) {
            Result.Error(ErrorType.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
            Result.Error(ErrorType.GENERIC_ERROR)
        }
    }
}