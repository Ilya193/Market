package ru.ikom.basket

import org.koin.dsl.module

val mealsBasketModule = module {
    factory<MealsBasketCacheDataSource> {
        MealsBasketCacheDataSourceImpl(get())
    }
}