package ru.kraz.market.presentation

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.kraz.market.R
import ru.kraz.market.core.BaseAdapter
import ru.kraz.market.core.BaseViewHolder
import ru.kraz.market.databinding.DelimiterLayoutBinding
import ru.kraz.market.databinding.ImageLayoutBinding
import ru.kraz.market.databinding.NoReviewsLayoutBinding
import ru.kraz.market.databinding.ReviewLayoutBinding
import ru.kraz.market.databinding.TextLayoutBinding

class ReviewsAdapter(
    private val context: Context
): BaseAdapter<ReviewUi, BaseViewHolder<ReviewUi>>() {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ReviewUi.Base -> REVIEW_VIEW_TYPE
/*            is ReviewUi.Image -> IMAGE_VIEW_TYPE
            is ReviewUi.Title -> TITLE_VIEW_TYPE
            is ReviewUi.Text -> TEXT_VIEW_TYPE
            is ReviewUi.Delimiter -> DELIMITER_VIEW_TYPE
            is ReviewUi.Section -> SECTION_VIEW_TYPE*/
            is ReviewUi.Empty -> NO_REVIEW_VIEW_TYPE
        }
    }

    inner class BaseReviewViewHolder(private val view: ReviewLayoutBinding) :
        BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvReview.text = item.textReview
        }
    }

/*    open class BaseTextViewHolder(private val view: TextLayoutBinding) : BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvText.text = item.textReview
        }
    }

    open inner class TitleTextViewHolder(private val view: TextLayoutBinding) : BaseTextViewHolder(view) {
        override fun bind(item: ReviewUi) {
            super.bind(item)
            view.tvText.textSize = 18f
        }
    }

    inner class SectionTextViewHolder(private val view: TextLayoutBinding) : TitleTextViewHolder(view) {
        override fun bind(item: ReviewUi) {
            super.bind(item)
            view.tvText.typeface = Typeface.DEFAULT_BOLD
        }
    }

    inner class ImageViewHolder(private val view: ImageLayoutBinding) : BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            item as ReviewUi.Image
            Glide.with(context)
                .load(item.url)
                .into(view.image)
        }
    }

    inner class DelimiterViewHolder(private val view: DelimiterLayoutBinding) : BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {}
    }*/

    inner class EmptyViewHolder(private val view: NoReviewsLayoutBinding) :
        BaseViewHolder<ReviewUi>(view.root) {
        override fun bind(item: ReviewUi) {
            view.tvMessage.text = view.root.context.getString(R.string.firt_review)
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
/*
            IMAGE_VIEW_TYPE -> ImageViewHolder(
                ImageLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TITLE_VIEW_TYPE -> TitleTextViewHolder(
                TextLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TEXT_VIEW_TYPE -> BaseTextViewHolder(
                TextLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            DELIMITER_VIEW_TYPE -> DelimiterViewHolder(
                DelimiterLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            SECTION_VIEW_TYPE -> SectionTextViewHolder(
                TextLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )*/

            else -> EmptyViewHolder(
                NoReviewsLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    private companion object {
        const val REVIEW_VIEW_TYPE = 1
        const val IMAGE_VIEW_TYPE = 2
        const val TITLE_VIEW_TYPE = 3
        const val TEXT_VIEW_TYPE = 4
        const val DELIMITER_VIEW_TYPE = 5
        const val SECTION_VIEW_TYPE = 6
        const val NO_REVIEW_VIEW_TYPE = 7
    }
}