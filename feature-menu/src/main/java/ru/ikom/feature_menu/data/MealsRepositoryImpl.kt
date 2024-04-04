package ru.ikom.feature_menu.data

import ru.ikom.basket.MealsBasketCacheDataSource
import ru.ikom.feature_menu.data.Mappers.toMealBasketData
import ru.ikom.feature_menu.data.Mappers.toMealDomain
import ru.ikom.feature_menu.data.Mappers.toMealsData
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
    override suspend fun fetchMeals(): LoadResult<List<MealDomain>> {
        return try {
            val meals = cloudDataSource.fetchMeals().toMealsData()
            mealsCacheDataSource.delete()
            mealsCacheDataSource.addMeals(meals)
            val mealsBasket = mealsBasketCacheDataSource.fetchMeals()
            val finalList = if (mealsBasket.isNotEmpty()) {
                meals.map { meal ->
                    val equal = mealsBasket.any { mealBasket -> mealBasket.idMeal == meal.idMeal }
                    if (equal) meal.copy(purchased = true)
                    else meal
                }
            } else meals
            LoadResult.Success(finalList.map { it.toMealDomain() })
        } catch (e: UnknownHostException) {
            result(ErrorType.NO_CONNECTION)
        } catch (e: Exception) {
            result(ErrorType.GENERIC_ERROR)
        }
    }

    override suspend fun add(meal: MealDomain) {
        mealsBasketCacheDataSource.add(meal.toMealBasketData())
    }

    override suspend fun delete(meal: MealDomain) {
        mealsBasketCacheDataSource.delete(meal.toMealBasketData())
    }

    private suspend fun result(error: ErrorType): LoadResult<List<MealDomain>> {
        val meals = mealsCacheDataSource.fetchMeals().map { it.toMealDomain() }
        return if (meals.isNotEmpty()) LoadResult.Success(meals)
        else LoadResult.Error(error)
    }

}