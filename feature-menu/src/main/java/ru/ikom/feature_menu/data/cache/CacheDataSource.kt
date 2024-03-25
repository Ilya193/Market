package ru.ikom.feature_menu.data.cache

interface CacheDataSource {
    suspend fun fetchMeals(): List<MealCache>
    suspend fun addMeals(meals: List<MealCache>)
    suspend fun delete()
}