package ru.kraz.market.core

interface Mapper<R, T> {
    fun map(data: R): T
}