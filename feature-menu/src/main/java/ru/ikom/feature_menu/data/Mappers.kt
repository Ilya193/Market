package ru.ikom.feature_menu.data

import ru.ikom.basket.MealBasketData
import ru.ikom.feature_menu.domain.MealDomain
import ru.ikom.meals.MealData

object Mappers {
    fun MealData.toMealDomain(): MealDomain =
        MealDomain(idMeal, strMeal, strMealThumb, purchased)

    fun MealDomain.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb, purchased)

    fun MealDomain.toMealBasketData(): MealBasketData =
        MealBasketData(idMeal, strMeal, strMealThumb, purchased)

    fun MealBasketData.toMealDomain(): MealDomain =
        MealDomain(idMeal, strMeal, strMealThumb, purchased)

}