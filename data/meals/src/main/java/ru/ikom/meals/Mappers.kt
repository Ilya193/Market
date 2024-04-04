package ru.ikom.meals

import ru.ikom.database.basket.MealBasketCache
import ru.ikom.database.meals.MealCache

object Mappers {

    fun MealCache.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb, purchased)

    fun MealBasketCache.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb, purchased)
}