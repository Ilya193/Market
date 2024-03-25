package ru.ikom.feature_menu.domain

interface MealsRepository {
    suspend fun fetchMeals(): LoadResult<List<MealDomain>>
}