package ru.kraz.market.core

interface Comparing<T> {
    fun same(item: T): Boolean
    fun sameContent(item: T): Boolean
}