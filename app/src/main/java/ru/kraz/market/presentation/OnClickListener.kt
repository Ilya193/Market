package ru.kraz.market.presentation

interface OnClickListener {
    fun onClick(product: ProductUi) {}
    fun onClick()
}