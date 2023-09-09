package ru.kraz.market.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.kraz.market.core.BaseAdapter
import ru.kraz.market.core.BaseViewHolder
import ru.kraz.market.databinding.FailLayoutBinding
import ru.kraz.market.databinding.ProductLayoutBinding

class ProductsAdapter(
    private val context: Context,
    private val onClickListener: OnClickListener,
) : BaseAdapter<ProductUi, BaseViewHolder<ProductUi>>() {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProductUi.Base -> PRODUCT_VIEW_TYPE
            else -> FAIL_VIEW_TYPE
        }
    }

    inner class BaseProductViewHolder(private val view: ProductLayoutBinding) :
        BaseViewHolder<ProductUi>(view.root) {
        override fun bind(item: ProductUi) {
            view.tvName.text = item.name

            Glide.with(context)
                .load(item.url)
                .centerCrop()
                .into(view.image)

            view.root.setOnClickListener {
                onClickListener.onClick(getItem(adapterPosition))
            }
        }
    }

    inner class FailViewHolder(private val view: FailLayoutBinding) :
        BaseViewHolder<ProductUi>(view.root) {
        override fun bind(item: ProductUi) {
            view.tvName.text = item.name

            view.btnRetry.setOnClickListener {
                onClickListener.onClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ProductUi> {
        return when (viewType) {
            PRODUCT_VIEW_TYPE -> BaseProductViewHolder(
                ProductLayoutBinding.inflate(
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
        const val PRODUCT_VIEW_TYPE = 1
        const val FAIL_VIEW_TYPE = 2
    }
}