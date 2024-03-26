package ru.ikom.feature_menu.di

import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.ikom.feature_menu.data.MealsRepositoryImpl
import ru.ikom.feature_menu.data.cache.CacheDataSource
import ru.ikom.feature_menu.data.cache.CacheDataSourceImpl
import ru.ikom.feature_menu.data.cache.MealsDao
import ru.ikom.feature_menu.data.cache.MealsDb
import ru.ikom.feature_menu.data.cloud.CloudDataSource
import ru.ikom.feature_menu.data.cloud.CloudDataSourceImpl
import ru.ikom.feature_menu.data.cloud.MealsService
import ru.ikom.feature_menu.domain.FetchMealsUseCase
import ru.ikom.feature_menu.domain.MealsRepository
import ru.ikom.feature_menu.presentation.MenuViewModel

val featureMenuModule = module {
    viewModel<MenuViewModel> {
        MenuViewModel(get(), get())
    }

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

    factory<CloudDataSource> {
        CloudDataSourceImpl(get())
    }

    factory<CacheDataSource> {
        CacheDataSourceImpl(get())
    }

    factory<MealsRepository> {
        MealsRepositoryImpl(get(), get())
    }

    factory<FetchMealsUseCase> {
        FetchMealsUseCase(get())
    }
}