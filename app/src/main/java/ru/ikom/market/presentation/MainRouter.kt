package ru.ikom.market.presentation

import ru.ikom.common.Router

interface MainRouter : Router {
    fun openMenu()
    fun openBasket()
}