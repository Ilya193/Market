package ru.ikom.feature_menu.data

import ru.ikom.meals.cache.MealCache
import ru.ikom.meals.cloud.MealCloud
import ru.ikom.meals.cloud.MealsCloud

object Mappers {
    private fun MealCloud.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb)

    fun MealsCloud.toMealsData(): List<MealData> = meals.map { it.toMealData() }

    fun MealCache.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb)
}