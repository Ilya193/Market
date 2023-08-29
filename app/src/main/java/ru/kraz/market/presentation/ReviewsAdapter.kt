package ru.kraz.market.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.kraz.market.core.BaseAdapter
import ru.kraz.market.core.BaseViewHolder
import ru.kraz.market.databinding.ProductFailLayoutBinding
import ru.kraz.market.databinding.ReviewLayoutBinding

class ReviewsAdapter : BaseAdapter<ReviewUi, BaseViewHolder<ReviewUi>>() {

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is ReviewUi.Base -> REVIEW_VIEW_TYPE
            else -> FAIL_VIEW_TYPE
        }
    }

    inner class BaseReviewViewHolder(private val view: ReviewLayoutBinding) :
        BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvReview.text = item.textReview
        }
    }

    inner class FailProductViewHolder(private val view: ProductFailLayoutBinding) :
        BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvName.text = item.textReview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ReviewUi> {
        return when(viewType) {
            REVIEW_VIEW_TYPE -> BaseReviewViewHolder(
                ReviewLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> FailProductViewHolder(
                ProductFailLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    private companion object {
        const val REVIEW_VIEW_TYPE = 1
        const val FAIL_VIEW_TYPE = 2
    }
}