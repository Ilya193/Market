package ru.kraz.market.presentation

import ru.kraz.market.domain.ReviewDomain
import ru.kraz.market.core.Mapper

class BaseToReviewUiMapper : Mapper<ReviewDomain, ReviewUi.Base> {
    override fun map(data: ReviewDomain): ReviewUi.Base =
        ReviewUi.Base(data.id, data.textReview, data.productId)
}