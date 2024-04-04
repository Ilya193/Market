package ru.ikom.basket

import ru.ikom.database.basket.MealBasketCache

data class MealBasketData(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val purchased: Boolean = false
) {

    fun toMealBasketCache(): MealBasketCache =
        MealBasketCache(idMeal, strMeal, strMealThumb, purchased)
}