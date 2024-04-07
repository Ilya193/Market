package ru.ikom.feature_basket.data

import ru.ikom.basket.MealsBasketCacheDataSource
import ru.ikom.feature_basket.data.Mappers.toMealBasketData
import ru.ikom.feature_basket.data.Mappers.toMealDomain
import ru.ikom.feature_basket.domain.ErrorType
import ru.ikom.feature_basket.domain.LoadResult
import ru.ikom.feature_basket.domain.MealDomain
import ru.ikom.feature_basket.domain.MealsBasketRepository

class MealsBasketRepositoryImpl(
    private val mealsBasketCacheDataSource: MealsBasketCacheDataSource
) : MealsBasketRepository {
    override suspend fun fetchMealsBasket(): LoadResult<List<MealDomain>> {
        return try {
            val meals = mealsBasketCacheDataSource.fetchMeals()
            LoadResult.Success(meals.map { it.toMealDomain() })
        } catch (e: Exception) {
            LoadResult.Error(ErrorType.GENERIC_ERROR)
        }
    }

    override suspend fun delete(meal: MealDomain) {
        mealsBasketCacheDataSource.delete(meal.toMealBasketData())
    }
}