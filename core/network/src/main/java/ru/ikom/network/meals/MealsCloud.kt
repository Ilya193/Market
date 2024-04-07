package ru.ikom.network.meals

import kotlinx.serialization.Serializable

@Serializable
data class MealsCloud(
    val meals: List<MealCloud>
)