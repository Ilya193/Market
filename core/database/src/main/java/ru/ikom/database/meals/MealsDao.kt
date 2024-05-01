package ru.ikom.database.meals

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDao {
    @Query("SELECT * FROM meals")
    fun fetchMeals(): Flow<List<MealCache>>

    @Insert
    suspend fun addMeals(meals: List<MealCache>)

    @Query("DELETE FROM meals")
    suspend fun delete()
}