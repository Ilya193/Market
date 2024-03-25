package ru.ikom.feature_menu.data

import ru.ikom.feature_menu.data.cache.CacheDataSource
import ru.ikom.feature_menu.data.cloud.CloudDataSource
import ru.ikom.feature_menu.domain.ErrorType
import ru.ikom.feature_menu.domain.LoadResult
import ru.ikom.feature_menu.domain.MealDomain
import ru.ikom.feature_menu.domain.MealsRepository
import java.net.UnknownHostException

class MealsRepositoryImpl(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
) : MealsRepository {
    override suspend fun fetchMeals(): LoadResult<List<MealDomain>> {
        return try {
            val meals = cloudDataSource.fetchMeals().toMealsData()
            cacheDataSource.delete()
            cacheDataSource.addMeals(meals.map { it.toMealCache() })
            LoadResult.Success(meals.map { it.toMealDomain() })
        } catch (e: UnknownHostException) {
            val meals = cacheDataSource.fetchMeals().map { it.toMealData() }
            result(meals, ErrorType.NO_CONNECTION)
        } catch (E: Exception) {
            val meals = cacheDataSource.fetchMeals().map { it.toMealData() }
            result(meals, ErrorType.GENERIC_ERROR)
        }
    }

    private fun result(meals: List<MealData>, error: ErrorType) =
        if (meals.isNotEmpty()) LoadResult.Success(meals.map { it.toMealDomain() })
        else LoadResult.Error(error)

}