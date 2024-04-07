package ru.ikom.feature_basket.domain

data class MealDomain(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val purchased: Boolean = false
)