package ru.ikom.meals.cache

class CacheDataSourceImpl(
    private val dao: MealsDao,
) : CacheDataSource {
    override suspend fun fetchMeals(): List<MealCache> = dao.fetchMeals()

    override suspend fun addMeals(meals: List<MealCache>) = dao.addMeals(meals)

    override suspend fun delete() = dao.delete()
}