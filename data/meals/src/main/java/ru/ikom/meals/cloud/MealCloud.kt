package ru.ikom.meals.cloud

import kotlinx.serialization.Serializable

@Serializable
data class MealCloud(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)