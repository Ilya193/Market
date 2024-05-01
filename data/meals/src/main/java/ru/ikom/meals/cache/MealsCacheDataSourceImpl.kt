package ru.ikom.meals.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ikom.database.meals.MealsDao
import ru.ikom.meals.Mappers.toMealData
import ru.ikom.meals.MealData

class MealsCacheDataSourceImpl(
    private val dao: MealsDao,
) : MealsCacheDataSource {
    override suspend fun fetchMeals(): Flow<List<MealData>> = dao.fetchMeals().map { it.map { it.toMealData() } }

    override suspend fun addMeals(meals: List<MealData>) = dao.addMeals(meals.map { it.toMealCache() })

    override suspend fun delete() = dao.delete()
}