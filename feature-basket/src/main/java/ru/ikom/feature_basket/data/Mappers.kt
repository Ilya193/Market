package ru.ikom.feature_basket.data

import ru.ikom.basket.MealBasketData
import ru.ikom.feature_basket.domain.MealDomain

object Mappers {
    fun MealBasketData.toMealDomain(): MealDomain =
        MealDomain(idMeal, strMeal, strMealThumb, purchased)

    fun MealDomain.toMealBasketData(): MealBasketData =
        MealBasketData(idMeal, strMeal, strMealThumb, purchased)
}