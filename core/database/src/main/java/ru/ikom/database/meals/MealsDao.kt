package ru.ikom.database.meals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MealsDao {
    @Query("SELECT * FROM meals")
    suspend fun fetchMeals(): List<MealCache>

    @Insert
    suspend fun addMeals(meals: List<MealCache>)

    @Query("DELETE FROM meals")
    suspend fun delete()
}