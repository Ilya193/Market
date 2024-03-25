package ru.ikom.feature_menu.data

import ru.ikom.feature_menu.data.cache.MealCache
import ru.ikom.feature_menu.domain.MealDomain

data class MealData(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) {
    fun toMealDomain(): MealDomain =
        MealDomain(idMeal, strMeal, strMealThumb)

    fun toMealCache(): MealCache =
        MealCache(idMeal, strMeal, strMealThumb)
}