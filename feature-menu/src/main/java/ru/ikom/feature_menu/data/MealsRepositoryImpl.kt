package ru.ikom.feature_menu.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import ru.ikom.basket.MealsBasketCacheDataSource
import ru.ikom.feature_menu.data.Mappers.toMealBasketData
import ru.ikom.feature_menu.data.Mappers.toMealData
import ru.ikom.feature_menu.data.Mappers.toMealDomain
import ru.ikom.feature_menu.domain.ErrorType
import ru.ikom.feature_menu.domain.LoadResult
import ru.ikom.feature_menu.domain.MealDomain
import ru.ikom.feature_menu.domain.MealsRepository
import ru.ikom.meals.cache.MealsCacheDataSource
import ru.ikom.meals.cloud.CloudDataSource
import java.net.UnknownHostException

class MealsRepositoryImpl(
    private val cloudDataSource: CloudDataSource,
    private val mealsCacheDataSource: MealsCacheDataSource,
    private val mealsBasketCacheDataSource: MealsBasketCacheDataSource
) : MealsRepository {

    override suspend fun fetchMeals(): Flow<LoadResult<List<MealDomain>>> {
        var meals: List<MealDomain> = emptyList()
        var isLoading = true

        val cacheFlow: Flow<LoadResult<List<MealDomain>>> = flow {
            mealsCacheDataSource.fetchMeals().collect {
                if (it.isNotEmpty()) {
                    val cache = mealsBasketCacheDataSource.fetchMeals().map { it.toMealDomain() }
                    meals = it.map { meal ->
                        val equal = cache.any { mealBasket -> mealBasket.idMeal == meal.idMeal }
                        if (equal) meal.copy(purchased = true)
                        else meal
                    }.map { it.toMealDomain() }.toList()
                    if (isLoading) emit(LoadResult.Loading(meals))
                    else emit(LoadResult.Success(meals))
                }
            }
        }

        val cloudFlow: Flow<LoadResult<List<MealDomain>>> = flow {
            try {
                meals = cloudDataSource.fetchMeals().map { it.toMealDomain() }
                isLoading = false
                mealsCacheDataSource.delete()
                mealsCacheDataSource.addMeals(meals.map { it.toMealData() })
            } catch (e: UnknownHostException) {
                isLoading = false
                emit(LoadResult.Error(meals, ErrorType.NO_CONNECTION))
            } catch (e: Exception) {
                isLoading = false
                emit(LoadResult.Error(meals, ErrorType.GENERIC_ERROR))
            }
        }

        return merge(cacheFlow, cloudFlow)
    }

    override suspend fun add(meal: MealDomain) {
        mealsBasketCacheDataSource.add(meal.toMealBasketData())
    }

    override suspend fun delete(meal: MealDomain) {
        mealsBasketCacheDataSource.delete(meal.toMealBasketData())
    }
}