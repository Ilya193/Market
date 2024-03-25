package ru.ikom.feature_menu.data.cloud

import retrofit2.http.GET

interface MealsService {
    @GET("api/json/v1/1/filter.php?c=Dessert")
    suspend fun fetchMeals(): MealsCloud
}