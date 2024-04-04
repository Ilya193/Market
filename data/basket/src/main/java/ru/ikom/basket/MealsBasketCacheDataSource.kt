package ru.ikom.basket

interface MealsBasketCacheDataSource {
    suspend fun fetchMeals(): List<MealBasketData>
    suspend fun add(meal: MealBasketData)
    suspend fun delete(meal: MealBasketData)
}