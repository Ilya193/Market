package ru.kraz.market.presentation

import ru.kraz.market.domain.ErrorType

interface ResourceProvider {
    fun getString(errorType: ErrorType): String
}