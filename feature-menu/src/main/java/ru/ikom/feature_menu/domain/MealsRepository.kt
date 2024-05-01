package ru.ikom.feature_menu.domain

import kotlinx.coroutines.flow.Flow

interface MealsRepository {
    suspend fun fetchMeals(): Flow<LoadResult<List<MealDomain>>>
    suspend fun add(meal: MealDomain)
    suspend fun delete(meal: MealDomain)
}