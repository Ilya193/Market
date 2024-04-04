package ru.ikom.meals.di

import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.ikom.meals.cache.MealsDao
import ru.ikom.meals.cache.MealsDb
import ru.ikom.meals.cloud.MealsService

val mealsModule = module {
    single<MealsService> {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MealsService::class.java)
    }

    single<MealsDao> {
        Room.databaseBuilder(get(), MealsDb::class.java, "meals_db").build().mealsDao()
    }
}