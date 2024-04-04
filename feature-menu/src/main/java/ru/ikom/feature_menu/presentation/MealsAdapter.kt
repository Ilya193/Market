package ru.ikom.feature_menu.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.ikom.common.BaseDiffUtil
import ru.ikom.common.BaseListAdapter
import ru.ikom.feature_menu.databinding.MealLayoutBinding

class MealsAdapter(
    private val buy: (Int) -> Unit
): BaseListAdapter<MealUi, MealsAdapter.ViewHolder>(DiffMeals()) {

    inner class ViewHolder(private val view: MealLayoutBinding) : BaseListAdapter.BaseViewHolder<MealUi>(view.root) {

        init {
            view.btnOrder.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) buy(adapterPosition)
            }
        }

        override fun bind(item: MealUi) {
            view.imageMeal.load(item.strMealThumb) {
                transformations(CircleCropTransformation())
                crossfade(true)
            }
            bindPurchased(item)
            view.tvNameMeal.text = item.strMeal
            view.tvDescriptionMeal.text = item.strMeal
        }

        fun bindPurchased(item: MealUi) {
            view.icPurchased.visibility = if (item.purchased) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(MealLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindPurchased(getItem(position))
    }
}

class DiffMeals : BaseDiffUtil<MealUi>()