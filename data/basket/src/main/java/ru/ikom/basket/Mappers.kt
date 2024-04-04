package ru.ikom.basket

import ru.ikom.database.basket.MealBasketCache

object Mappers {

    fun MealBasketCache.toMealData(): MealBasketData =
        MealBasketData(idMeal, strMeal, strMealThumb, purchased)


}