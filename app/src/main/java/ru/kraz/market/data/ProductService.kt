package ru.kraz.market.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductService {
    @GET("products")
    suspend fun fetchProducts(): List<ProductCloud>

    @POST("saveReview")
    suspend fun sendReview(@Body review: SaveReviewCloud): List<ReviewCloud>

}