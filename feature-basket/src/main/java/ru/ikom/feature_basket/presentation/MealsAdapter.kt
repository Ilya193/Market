package ru.ikom.feature_basket.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.ikom.common.BaseDiffUtil
import ru.ikom.common.BaseListAdapter
import ru.ikom.feature_basket.databinding.MealBasketLayoutBinding

class MealsAdapter(
    private val buy: (Int) -> Unit
): BaseListAdapter<MealUi, MealsAdapter.ViewHolder>(DiffMeals()) {

    inner class ViewHolder(private val view: MealBasketLayoutBinding) : BaseListAdapter.BaseViewHolder<MealUi>(view.root) {

        init {
            view.ivRemove.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) buy(adapterPosition)
            }
        }

        override fun bind(item: MealUi) {
            view.imageMeal.load(item.strMealThumb) {
                transformations(CircleCropTransformation())
                crossfade(true)
            }
            view.tvNameMeal.text = item.strMeal
            view.tvDescriptionMeal.text = item.strMeal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(MealBasketLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

class DiffMeals : BaseDiffUtil<MealUi>()