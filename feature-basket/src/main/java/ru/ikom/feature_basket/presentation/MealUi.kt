package ru.ikom.feature_basket.presentation

import ru.ikom.common.DelegateItem

data class MealUi(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val purchased: Boolean = false
): DelegateItem {
    override fun id(item: DelegateItem): Boolean =
        idMeal == (item as MealUi).idMeal

    override fun compareTo(item: DelegateItem): Boolean =
        this == (item as MealUi)

    override fun changePayload(item: DelegateItem): Any =
        purchased != (item as MealUi).purchased
}