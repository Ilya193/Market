package ru.ikom.meals.cache

import ru.ikom.meals.cache.MealCache

interface CacheDataSource {
    suspend fun fetchMeals(): List<MealCache>
    suspend fun addMeals(meals: List<MealCache>)
    suspend fun delete()
}