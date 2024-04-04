package ru.ikom.meals.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.ikom.meals.cache.MealCache

@Dao
interface MealsDao {
    @Query("SELECT * FROM meals")
    suspend fun fetchMeals(): List<MealCache>

    @Insert
    suspend fun addMeals(meals: List<MealCache>)

    @Query("DELETE FROM meals")
    suspend fun delete()
}