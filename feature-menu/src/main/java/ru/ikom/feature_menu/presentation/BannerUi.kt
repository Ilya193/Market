package ru.ikom.feature_menu.presentation

import ru.ikom.common.DelegateItem

data class BannerUi(
    val id: Int,
    val content: String = "",
) : DelegateItem {
    override fun id(item: DelegateItem): Boolean =
        id == (item as BannerUi).id

    override fun compareTo(item: DelegateItem): Boolean =
        this == (item as BannerUi)

    override fun changePayload(item: DelegateItem): Any = false

}