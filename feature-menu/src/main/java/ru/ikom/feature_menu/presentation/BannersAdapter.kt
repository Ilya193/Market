package ru.ikom.feature_menu.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import ru.ikom.common.BaseDiffUtil
import ru.ikom.common.BaseListAdapter
import ru.ikom.feature_menu.R
import ru.ikom.feature_menu.databinding.BannerLayoutBinding

class BannersAdapter : BaseListAdapter<BannerUi, BannersAdapter.ViewHolder>(DiffBanners()) {

    inner class ViewHolder(private val view: BannerLayoutBinding) : BaseListAdapter.BaseViewHolder<BannerUi>(view.root) {
        override fun bind(item: BannerUi) {
            view.img.load(R.drawable.banner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(BannerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}

class DiffBanners : BaseDiffUtil<BannerUi>()