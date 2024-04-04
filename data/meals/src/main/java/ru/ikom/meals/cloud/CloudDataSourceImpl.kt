package ru.ikom.meals.cloud

class CloudDataSourceImpl(
    private val service: MealsService
) : CloudDataSource {
    override suspend fun fetchMeals(): MealsCloud = service.fetchMeals()
}