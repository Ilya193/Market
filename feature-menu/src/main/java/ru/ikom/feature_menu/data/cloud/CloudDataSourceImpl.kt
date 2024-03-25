package ru.ikom.feature_menu.data.cloud

class CloudDataSourceImpl(
    private val service: MealsService
) : CloudDataSource {
    override suspend fun fetchMeals(): MealsCloud = service.fetchMeals()
}