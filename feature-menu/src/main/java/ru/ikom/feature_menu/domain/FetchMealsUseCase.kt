package ru.ikom.feature_menu.domain

class FetchMealsUseCase(
    private val repository: MealsRepository
) {

    suspend operator fun invoke() = repository.fetchMeals()
}