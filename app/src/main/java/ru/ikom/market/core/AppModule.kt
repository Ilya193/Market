package ru.ikom.market.core

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.feature_menu.presentation.MenuRouter
import ru.ikom.market.presentation.MainViewModel
import ru.ikom.market.presentation.Navigation
import ru.ikom.market.presentation.Screen

val appModule = module {
    val navigation = Navigation.Base()

    single<Navigation<Screen>> {
        navigation
    }

    single<MenuRouter> {
        navigation
    }

    viewModel<MainViewModel> {
        MainViewModel(get())
    }
}