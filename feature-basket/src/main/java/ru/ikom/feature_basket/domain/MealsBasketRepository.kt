package ru.ikom.feature_basket.domain

interface MealsBasketRepository {
    suspend fun fetchMealsBasket(): LoadResult<List<MealDomain>>
    suspend fun delete(meal: MealDomain)
}
