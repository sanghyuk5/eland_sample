package com.pionnet.eland.ui.main.tabStoreShop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.databinding.ViewItemStoreShopCategoryBinding
import com.pionnet.eland.data.StoreShopData
import com.pionnet.eland.utils.GlideApp

class StoreShopCategoryStickyAdapter
    : ListAdapter<StoreShopData.Data.CategoryGoods, StoreShopCategoryStickyAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewItemStoreShopCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener {
            EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_STORE_SHOP, position))
        }
    }

    fun setSelected(position: Int) {
        currentList.forEach {
            it.isSelected = false
        }

        currentList[position].isSelected = true
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewItemStoreShopCategoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StoreShopData.Data.CategoryGoods) = with(binding) {
            if (data.isSelected) {
                GlideApp.with(itemView.context).load("https:" + data.activeImageUrl).into(ivCategory)
                ivBar.visibility = View.VISIBLE
            } else {
                GlideApp.with(itemView.context).load("https:" + data.deActiveImageUrl).into(ivCategory)
                ivBar.visibility = View.GONE
            }

            tvCategory.text = data.ctgNm
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<StoreShopData.Data.CategoryGoods>() {
        override fun areItemsTheSame(oldItem: StoreShopData.Data.CategoryGoods, newItem: StoreShopData.Data.CategoryGoods): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: StoreShopData.Data.CategoryGoods, newItem: StoreShopData.Data.CategoryGoods): Boolean =
            oldItem == newItem
    }
}

