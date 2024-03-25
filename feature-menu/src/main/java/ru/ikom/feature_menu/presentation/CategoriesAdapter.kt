package ru.ikom.feature_menu.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.ikom.common.BaseDiffUtil
import ru.ikom.common.BaseListAdapter
import ru.ikom.feature_menu.R
import ru.ikom.feature_menu.databinding.CategoryLayoutBinding

class CategoriesAdapter(
    private val click: (Int) -> Unit
) : BaseListAdapter<CategoryUi, CategoriesAdapter.ViewHolder>(DiffCategories()) {

    inner class ViewHolder(private val view: CategoryLayoutBinding) : BaseListAdapter.BaseViewHolder<CategoryUi>(view.root) {

        init {
            view.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) click(adapterPosition)
            }
        }

        override fun bind(item: CategoryUi) {
            view.category.text = item.text
            bindCurrentCategory(item)
        }

        fun bindCurrentCategory(item: CategoryUi) {
            if (item.selected) {
                view.category.setTextColor(ContextCompat.getColor(view.category.context, R.color.textColorCard))
                view.root.setCardBackgroundColor(ContextCompat.getColor(view.root.context, R.color.colorCard))
            }
            else {
                view.category.setTextColor(ContextCompat.getColor(view.category.context, android.R.color.darker_gray))
                view.root.setCardBackgroundColor(ContextCompat.getColor(view.root.context, android.R.color.white))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(CategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindCurrentCategory(getItem(position))
    }
}

class DiffCategories : BaseDiffUtil<CategoryUi>()