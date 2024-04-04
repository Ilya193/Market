package ru.ikom.feature_menu.data

import ru.ikom.feature_menu.data.Mappers.toMealData
import ru.ikom.feature_menu.data.Mappers.toMealsData
import ru.ikom.meals.cache.CacheDataSource
import ru.ikom.feature_menu.domain.ErrorType
import ru.ikom.feature_menu.domain.LoadResult
import ru.ikom.feature_menu.domain.MealDomain
import ru.ikom.feature_menu.domain.MealsRepository
import ru.ikom.meals.cloud.CloudDataSource
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
            result(ErrorType.NO_CONNECTION)
        } catch (e: Exception) {
            result(ErrorType.GENERIC_ERROR)
        }
    }

    private suspend fun result(error: ErrorType): LoadResult<List<MealDomain>> {
        val meals = cacheDataSource.fetchMeals().map { it.toMealData() }
        return if (meals.isNotEmpty()) LoadResult.Success(meals.map { it.toMealDomain() })
        else LoadResult.Error(error)
    }

}