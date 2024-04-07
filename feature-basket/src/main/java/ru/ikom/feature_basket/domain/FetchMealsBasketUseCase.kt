package ru.ikom.feature_basket.domain

class FetchMealsBasketUseCase(
    private val repository: MealsBasketRepository
) {

    suspend operator fun invoke(): LoadResult<List<MealDomain>> = repository.fetchMealsBasket()
}