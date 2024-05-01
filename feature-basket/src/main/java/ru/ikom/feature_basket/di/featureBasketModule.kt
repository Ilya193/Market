package ru.ikom.feature_basket.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.feature_basket.data.MealsBasketRepositoryImpl
import ru.ikom.feature_basket.domain.FetchMealsBasketUseCase
import ru.ikom.feature_basket.domain.MealsBasketRepository
import ru.ikom.feature_basket.presentation.BasketViewModel
import ru.ikom.feature_basket.presentation.DeleteMealUseCase

val featureBasketModule = module {
    factory<MealsBasketRepository> {
        MealsBasketRepositoryImpl(get())
    }

    factory<FetchMealsBasketUseCase> {
        FetchMealsBasketUseCase(get())
    }

    factory<DeleteMealUseCase> {
        DeleteMealUseCase(get())
    }

    viewModel<BasketViewModel> {
        BasketViewModel(get(), get(), get())
    }
}