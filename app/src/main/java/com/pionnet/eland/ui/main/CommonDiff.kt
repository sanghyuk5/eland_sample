package com.pionnet.eland.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.pionnet.eland.model.Banner
import com.pionnet.eland.model.Goods

class BannerDiffCallback : DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean =
        oldItem == newItem
}

class GoodsDiffCallback : DiffUtil.ItemCallback<Goods>() {
    override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean =
        oldItem == newItem
}

