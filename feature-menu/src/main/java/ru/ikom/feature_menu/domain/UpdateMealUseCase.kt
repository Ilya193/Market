package ru.ikom.feature_menu.domain

class UpdateMealUseCase(
    private val repository: MealsRepository
) {
    suspend operator fun invoke(meal: MealDomain) =
        if (meal.purchased) repository.add(meal)
        else repository.delete(meal)
}