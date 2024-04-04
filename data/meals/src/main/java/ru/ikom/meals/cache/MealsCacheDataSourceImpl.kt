package ru.ikom.meals.cache

import ru.ikom.database.meals.MealsDao
import ru.ikom.meals.Mappers.toMealData
import ru.ikom.meals.MealData

class MealsCacheDataSourceImpl(
    private val dao: MealsDao,
) : MealsCacheDataSource {
    override suspend fun fetchMeals(): List<MealData> = dao.fetchMeals().map { it.toMealData() }

    override suspend fun addMeals(meals: List<MealData>) = dao.addMeals(meals.map { it.toMealCache() })

    override suspend fun delete() = dao.delete()
}