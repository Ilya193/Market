package ru.ikom.network.meals

import kotlinx.serialization.Serializable

@Serializable
data class MealCloud(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val purchased: Boolean = false
)