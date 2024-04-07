package ru.ikom.meals

import ru.ikom.database.basket.MealBasketCache
import ru.ikom.database.meals.MealCache
import ru.ikom.network.meals.MealCloud
import ru.ikom.network.meals.MealsCloud

object Mappers {

    fun MealCache.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb, purchased)

    fun MealCloud.toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb, purchased)

    fun MealsCloud.toMealsData(): List<MealData> = meals.map { it.toMealData() }

}