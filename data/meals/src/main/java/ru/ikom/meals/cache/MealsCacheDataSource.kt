package ru.ikom.meals.cache

import kotlinx.coroutines.flow.Flow
import ru.ikom.meals.MealData

interface MealsCacheDataSource {
    suspend fun fetchMeals(): Flow<List<MealData>>
    suspend fun addMeals(meals: List<MealData>)
    suspend fun delete()
}