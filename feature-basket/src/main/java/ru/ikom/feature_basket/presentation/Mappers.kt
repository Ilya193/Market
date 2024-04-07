package ru.ikom.feature_basket.presentation

import ru.ikom.feature_basket.R
import ru.ikom.feature_basket.domain.ErrorType
import ru.ikom.feature_basket.domain.MealDomain

object Mappers {

    fun MealDomain.toMealUi(): MealUi =
        MealUi(idMeal, strMeal, strMealThumb, purchased)

    fun MealUi.toMealDomain(): MealDomain =
        MealDomain(idMeal, strMeal, strMealThumb, purchased)

    fun ErrorType.getData(): Int {
        return when (this) {
            ErrorType.NO_CONNECTION -> R.string.no_connection
            ErrorType.GENERIC_ERROR -> R.string.generic_error
        }
    }
}