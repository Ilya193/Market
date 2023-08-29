package ru.kraz.market.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kraz.market.data.ProductRepository

interface ProductsInteractor {
    suspend fun fetchProduct(): Flow<ProductsDomain>
    suspend fun sendReview(textReview: String, productId: Int): Flow<ReviewsDomain>

    class Base(
        private val productRepository: ProductRepository
    ) : ProductsInteractor {
        override suspend fun fetchProduct(): Flow<ProductsDomain> = flow {
            productRepository.fetchProducts().collect {
                emit(it.map())
            }
        }

        override suspend fun sendReview(textReview: String, productId: Int): Flow<ReviewsDomain> = flow {
            productRepository.sendReview(textReview, productId).collect {
                emit(it.map())
            }
        }
    }

}