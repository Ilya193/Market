package ru.ikom.feature_menu.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.feature_menu.data.MealsRepositoryImpl
import ru.ikom.feature_menu.domain.FetchMealsUseCase
import ru.ikom.feature_menu.domain.MealsRepository
import ru.ikom.feature_menu.presentation.MenuViewModel
import ru.ikom.meals.cache.CacheDataSource
import ru.ikom.meals.cache.CacheDataSourceImpl
import ru.ikom.meals.cloud.CloudDataSource
import ru.ikom.meals.cloud.CloudDataSourceImpl
import ru.ikom.meals.di.mealsModule

val featureMenuModule = module {
    viewModel<MenuViewModel> {
        MenuViewModel(get(), get())
    }

    includes(mealsModule)

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