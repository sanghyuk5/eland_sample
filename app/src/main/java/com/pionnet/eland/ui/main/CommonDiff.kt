package com.pionnet.eland.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.pionnet.eland.data.Banner
import com.pionnet.eland.data.Goods
import com.pionnet.eland.data.ViewTypeDataSet

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

class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}

class ModuleDiffCallback : DiffUtil.ItemCallback<ViewTypeDataSet>() {
    override fun areItemsTheSame(oldItem: ViewTypeDataSet, newItem: ViewTypeDataSet): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: ViewTypeDataSet, newItem: ViewTypeDataSet): Boolean = oldItem == newItem
}
