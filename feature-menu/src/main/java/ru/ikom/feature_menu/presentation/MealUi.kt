package ru.ikom.feature_menu.presentation

import ru.ikom.common.DelegateItem
import ru.ikom.feature_menu.domain.MealDomain

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

    fun toMealDomain(): MealDomain = MealDomain(idMeal, strMeal, strMealThumb, purchased)
}