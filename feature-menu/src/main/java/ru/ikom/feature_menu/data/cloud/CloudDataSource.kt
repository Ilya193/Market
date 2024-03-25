package ru.ikom.feature_menu.data.cloud

interface CloudDataSource {
    suspend fun fetchMeals(): MealsCloud
}