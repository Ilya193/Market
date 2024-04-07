package ru.ikom.meals.di

import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.ikom.database.MealsDb
import ru.ikom.database.basket.BasketDao
import ru.ikom.database.meals.MealsDao
import ru.ikom.meals.cache.MealsCacheDataSource
import ru.ikom.meals.cache.MealsCacheDataSourceImpl
import ru.ikom.meals.cloud.CloudDataSource
import ru.ikom.meals.cloud.CloudDataSourceImpl
import ru.ikom.network.meals.MealsService

val mealsModule = module {
    single<MealsService> {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MealsService::class.java)
    }

    factory<MealsCacheDataSource> {
        MealsCacheDataSourceImpl(get())
    }

    factory<CloudDataSource> {
        CloudDataSourceImpl(get())
    }

    single<MealsDb> {
        Room.databaseBuilder(get(), MealsDb::class.java, "meals_db").build()
    }

    single<MealsDao> {
        get<MealsDb>().mealsDao()
    }

    single<BasketDao> {
        get<MealsDb>().basketDao()
    }
}