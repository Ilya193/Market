package ru.kraz.market.presentation

interface OnClickProductListener {
    fun onClick(product: ProductUi)
    fun onClick()
}