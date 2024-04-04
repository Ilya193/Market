package ru.ikom.feature_menu.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.basket.MealsBasketCacheDataSource
import ru.ikom.basket.MealsBasketCacheDataSourceImpl
import ru.ikom.basket.mealsBasketModule
import ru.ikom.feature_menu.data.MealsRepositoryImpl
import ru.ikom.feature_menu.domain.FetchMealsUseCase
import ru.ikom.feature_menu.domain.MealsRepository
import ru.ikom.feature_menu.domain.UpdateMealUseCase
import ru.ikom.feature_menu.presentation.MenuViewModel
import ru.ikom.meals.cache.MealsCacheDataSource
import ru.ikom.meals.cache.MealsCacheDataSourceImpl
import ru.ikom.meals.cloud.CloudDataSource
import ru.ikom.meals.cloud.CloudDataSourceImpl
import ru.ikom.meals.di.mealsModule

val featureMenuModule = module {
    viewModel<MenuViewModel> {
        MenuViewModel(get(), get(), get())
    }

    includes(mealsModule)
    includes(mealsBasketModule)

    factory<CloudDataSource> {
        CloudDataSourceImpl(get())
    }

    factory<MealsRepository> {
        MealsRepositoryImpl(get(), get(), get())
    }

    factory<FetchMealsUseCase> {
        FetchMealsUseCase(get())
    }

    factory<UpdateMealUseCase> {
        UpdateMealUseCase(get())
    }
}