package ru.ikom.feature_menu.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import ru.ikom.common.BaseDiffUtil
import ru.ikom.common.BaseListAdapter
import ru.ikom.feature_menu.databinding.MealLayoutBinding

class MealsAdapter: BaseListAdapter<MealUi, MealsAdapter.ViewHolder>(DiffMeals()) {

    inner class ViewHolder(private val view: MealLayoutBinding) : BaseListAdapter.BaseViewHolder<MealUi>(view.root) {

        override fun bind(item: MealUi) {
            view.imageMeal.load(item.strMealThumb) {
                transformations(CircleCropTransformation())
            }
            view.tvNameMeal.text = item.strMeal
            view.tvDescriptionMeal.text = item.strMeal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(MealLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

class DiffMeals : BaseDiffUtil<MealUi>()