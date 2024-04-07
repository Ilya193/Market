package ru.ikom.meals.cloud

import ru.ikom.meals.MealData

interface CloudDataSource {
    suspend fun fetchMeals(): List<MealData>
}