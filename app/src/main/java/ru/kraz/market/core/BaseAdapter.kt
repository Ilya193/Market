package ru.kraz.market.core

import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Comparing<T>, E : BaseViewHolder<T>> : ListAdapter<T, E>(
    DiffUtilCallback()
) {
    override fun onBindViewHolder(holder: E, position: Int) {
        holder.bind(getItem(position))
    }
}

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}