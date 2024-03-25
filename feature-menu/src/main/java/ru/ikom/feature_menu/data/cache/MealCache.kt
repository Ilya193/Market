package ru.ikom.feature_menu.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.ikom.feature_menu.data.MealData

@Entity(tableName = "meals")
data class MealCache(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) {
    fun toMealData(): MealData =
        MealData(idMeal, strMeal, strMealThumb)
}