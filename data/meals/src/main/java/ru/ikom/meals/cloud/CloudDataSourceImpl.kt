package ru.ikom.meals.cloud

import ru.ikom.meals.Mappers.toMealsData
import ru.ikom.meals.MealData
import ru.ikom.network.meals.MealsService

class CloudDataSourceImpl(
    private val service: MealsService
) : CloudDataSource {
    override suspend fun fetchMeals(): List<MealData> = service.fetchMeals().toMealsData()
}