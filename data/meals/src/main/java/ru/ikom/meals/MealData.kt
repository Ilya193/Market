package ru.ikom.meals

import ru.ikom.database.meals.MealCache

data class MealData(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val purchased: Boolean = false
) {

    fun toMealCache(): MealCache =
        MealCache(idMeal, strMeal, strMealThumb, purchased)
}