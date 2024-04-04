package ru.ikom.meals.cloud

interface CloudDataSource {
    suspend fun fetchMeals(): MealsCloud
}