package ru.kraz.market.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductsService {
    @GET("products")
    suspend fun fetchProducts(): List<ProductCloud>

    @POST("saveReview")
    suspend fun sendReview(@Body review: SaveReviewCloud): List<ReviewCloud>

    @POST("reviews")
    suspend fun fetchReviews(@Query("productId") productId: Int): List<ReviewCloud>

}