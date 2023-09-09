package ru.kraz.market.domain

data class ReviewDomain(
    val id: Int,
    val textReview: String,
    val productId: Int,
)