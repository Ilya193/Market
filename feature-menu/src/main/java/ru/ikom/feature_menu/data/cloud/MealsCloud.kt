package ru.ikom.feature_menu.data.cloud

import kotlinx.serialization.Serializable

@Serializable
data class MealsCloud(
    val meals: List<MealCloud>
) {
    fun toMealsData() = meals.map { it.toMealData() }
}