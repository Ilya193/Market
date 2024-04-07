package ru.ikom.feature_basket.presentation

import ru.ikom.feature_basket.domain.MealDomain
import ru.ikom.feature_basket.domain.MealsBasketRepository

class DeleteMealUseCase(
    private val repository: MealsBasketRepository
) {

    suspend operator fun invoke(meal: MealDomain) = repository.delete(meal)
}
