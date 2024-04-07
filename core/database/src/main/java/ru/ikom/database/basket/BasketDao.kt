package ru.ikom.database.basket

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {
    @Query("SELECT * FROM baskets")
    suspend fun fetchMeals(): List<MealBasketCache>

    @Query("SELECT * FROM baskets")
    fun fetchMealsWithFlow(): Flow<List<MealBasketCache>>

    @Insert
    suspend fun add(meal: MealBasketCache)

    @Delete
    suspend fun delete(meal: MealBasketCache)
}