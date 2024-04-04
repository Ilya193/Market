package ru.ikom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ikom.database.basket.BasketDao
import ru.ikom.database.basket.MealBasketCache
import ru.ikom.database.meals.MealCache
import ru.ikom.database.meals.MealsDao

@Database(entities = [MealCache::class, MealBasketCache::class], version = 1)
abstract class MealsDb : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
    abstract fun basketDao(): BasketDao
}