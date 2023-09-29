package ru.kraz.market.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.kraz.market.core.BaseAdapter
import ru.kraz.market.core.BaseViewHolder
import ru.kraz.market.databinding.ProductLayoutBinding

class ProductsAdapter(
    private val context: Context,
    private val onClickListener: OnClickListener,
) : BaseAdapter<ProductUi, BaseViewHolder<ProductUi>>() {

    inner class ProductViewHolder(private val view: ProductLayoutBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ProductUi> {
        return ProductViewHolder(
            ProductLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}