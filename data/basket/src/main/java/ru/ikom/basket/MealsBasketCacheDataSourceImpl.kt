package ru.ikom.basket

import ru.ikom.basket.Mappers.toMealData
import ru.ikom.database.basket.BasketDao

class MealsBasketCacheDataSourceImpl(
    private val dao: BasketDao
) : MealsBasketCacheDataSource {
    override suspend fun fetchMeals(): List<MealBasketData> = dao.fetchMeals().map { it.toMealData() }
    override suspend fun add(meal: MealBasketData) = dao.add(meal.toMealBasketCache())
    override suspend fun delete(meal: MealBasketData) = dao.delete(meal.toMealBasketCache())
}