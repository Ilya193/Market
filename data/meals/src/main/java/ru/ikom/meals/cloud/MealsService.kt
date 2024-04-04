package ru.ikom.meals.cloud

import retrofit2.http.GET

interface MealsService {
    @GET("api/json/v1/1/filter.php?c=Dessert")
    suspend fun fetchMeals(): MealsCloud
}