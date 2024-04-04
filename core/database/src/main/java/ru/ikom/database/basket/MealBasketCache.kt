package ru.ikom.database.basket

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "baskets")
data class MealBasketCache(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val purchased: Boolean = false
)