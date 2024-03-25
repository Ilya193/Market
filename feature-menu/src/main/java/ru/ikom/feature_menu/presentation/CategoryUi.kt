package ru.ikom.feature_menu.presentation

import ru.ikom.common.DelegateItem

data class CategoryUi(
    val id: Int,
    val text: String,
    val selected: Boolean = false,
) : DelegateItem {
    override fun id(item: DelegateItem): Boolean =
        id == (item as CategoryUi).id

    override fun compareTo(item: DelegateItem): Boolean =
        this == (item as CategoryUi)

    override fun changePayload(item: DelegateItem): Any =
        selected != (item as CategoryUi).selected
}