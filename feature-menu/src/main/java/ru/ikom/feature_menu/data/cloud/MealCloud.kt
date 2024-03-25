package ru.ikom.feature_menu.data.cloud

import kotlinx.serialization.Serializable
import ru.ikom.feature_menu.data.MealData

@Serializable
data class MealCloud(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) {
    fun toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb)
}