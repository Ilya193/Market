package ru.ikom.feature_menu.data

import ru.ikom.basket.MealBasketData
import ru.ikom.feature_menu.domain.MealDomain
import ru.ikom.meals.MealData
import ru.ikom.meals.cloud.MealCloud
import ru.ikom.meals.cloud.MealsCloud

object Mappers {
    fun MealData.toMealDomain(): MealDomain =
        MealDomain(idMeal, strMeal, strMealThumb, purchased)

    fun MealCloud.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb, purchased)

    fun MealDomain.toMealBasketData(): MealBasketData =
        MealBasketData(idMeal, strMeal, strMealThumb, purchased)

    fun MealsCloud.toMealsData(): List<MealData> = meals.map { it.toMealData() }
}