package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemMdRecommendCategoryBinding
import com.pionnet.eland.databinding.ViewItemMdRecommendGoodBinding
import com.pionnet.eland.databinding.ViewItemStoreShopCategoryBinding
import com.pionnet.eland.databinding.ViewStoreShopCategoryModuleBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.model.StoreShopData
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat

class StoreShopCategoryViewHolder(
    private val binding: ViewStoreShopCategoryModuleBinding
) : BaseViewHolder(binding.root) {

    private val tabClickCallback: ItemClickIntCallback = { index ->

    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopCategoryData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopCategoryData) = with(binding) {
        rvCategory.apply {
            adapter = CategoryAdapter(tabClickCallback).apply {
                submitList(data.categoryData)
            }
        }
    }

    private inner class CategoryAdapter(private val tabClickCallback: ItemClickIntCallback)
        : ListAdapter<StoreShopData.Data.CategoryGoods, CategoryAdapter.ViewHolder>(DiffCallback()) {

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
                tabClickCallback.invoke(position)
            }
        }

        private inner class ViewHolder(val binding: ViewItemStoreShopCategoryBinding)
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
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<StoreShopData.Data.CategoryGoods>() {
        override fun areItemsTheSame(oldItem: StoreShopData.Data.CategoryGoods, newItem: StoreShopData.Data.CategoryGoods): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: StoreShopData.Data.CategoryGoods, newItem: StoreShopData.Data.CategoryGoods): Boolean =
            oldItem == newItem
    }



}