package ru.ikom.meals.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealCache(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)