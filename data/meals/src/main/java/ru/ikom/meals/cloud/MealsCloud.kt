package ru.ikom.meals.cloud

import kotlinx.serialization.Serializable

@Serializable
data class MealsCloud(
    val meals: List<MealCloud>
)