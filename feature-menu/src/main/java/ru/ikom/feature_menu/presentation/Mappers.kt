package ru.ikom.feature_menu.presentation

import ru.ikom.feature_menu.R
import ru.ikom.feature_menu.domain.ErrorType
import ru.ikom.feature_menu.domain.MealDomain

object Mappers {
    fun MealDomain.toMealUi(): MealUi =
        MealUi(idMeal, strMeal, strMealThumb, purchased)

    fun ErrorType.getData(): Int {
        return when (this) {
            ErrorType.NO_CONNECTION -> R.string.no_connection
            ErrorType.GENERIC_ERROR -> R.string.generic_error
        }
    }
}