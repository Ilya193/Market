package ru.ikom.meals.cache

import ru.ikom.meals.MealData

interface MealsCacheDataSource {
    suspend fun fetchMeals(): List<MealData>
    suspend fun addMeals(meals: List<MealData>)
    suspend fun delete()
}