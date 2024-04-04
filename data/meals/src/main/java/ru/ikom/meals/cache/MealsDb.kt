package ru.ikom.meals.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MealCache::class], version = 1)
abstract class MealsDb : RoomDatabase() {
    abstract fun mealsDao(): MealsDao
}