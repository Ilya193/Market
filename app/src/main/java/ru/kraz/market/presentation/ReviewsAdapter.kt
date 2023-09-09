package ru.kraz.market.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.kraz.market.core.BaseAdapter
import ru.kraz.market.core.BaseViewHolder
import ru.kraz.market.databinding.FailLayoutBinding
import ru.kraz.market.databinding.NoReviewsLayoutBinding
import ru.kraz.market.databinding.ProductLayoutBinding
import ru.kraz.market.databinding.ReviewLayoutBinding

class ReviewsAdapter(
    private val onClickListener: OnClickListener
) : BaseAdapter<ReviewUi, BaseViewHolder<ReviewUi>>() {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ReviewUi.Base -> REVIEW_VIEW_TYPE
            is ReviewUi.Empty -> NO_REVIEW_VIEW_TYPE
            else -> FAIL_VIEW_TYPE
        }
    }

    inner class BaseReviewViewHolder(private val view: ReviewLayoutBinding) :
        BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvReview.text = item.textReview
        }
    }

    inner class FailViewHolder(private val view: FailLayoutBinding) :
        BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvName.text = item.textReview

            view.btnRetry.setOnClickListener {
                onClickListener.onClick()
            }
        }
    }

    inner class EmptyViewHolder(private val view: NoReviewsLayoutBinding) :
        BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvMessage.text = item.textReview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ReviewUi> {
        return when (viewType) {
            REVIEW_VIEW_TYPE -> BaseReviewViewHolder(
                ReviewLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            NO_REVIEW_VIEW_TYPE -> EmptyViewHolder(
                NoReviewsLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> FailViewHolder(
                FailLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    private companion object {
        const val REVIEW_VIEW_TYPE = 1
        const val NO_REVIEW_VIEW_TYPE = 2
        const val FAIL_VIEW_TYPE = 3
    }
}