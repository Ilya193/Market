package ru.ikom.feature_menu.domain

interface MealsRepository {
    suspend fun fetchMeals(): LoadResult<List<MealDomain>>
    suspend fun add(meal: MealDomain)
    suspend fun delete(meal: MealDomain)
}