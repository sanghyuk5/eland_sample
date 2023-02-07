package com.pionnet.eland.ui.leftmenu.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemLeftMenuShopBinding
import com.pionnet.eland.databinding.ViewLeftMenuShopModuleBinding
import com.pionnet.eland.model.LeftMenuData
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.GlideApp

class LeftMenuShopViewHolder(
    private val binding: ViewLeftMenuShopModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val dataList = it.toMutableList().checkItemsAre<LeftMenuData.Data.Shop>()
            dataList?.let { data ->
                initView(data)
            }
        }
    }

    private fun initView(data: MutableList<LeftMenuData.Data.Shop>) = with(binding) {
        rvShop.adapter = ShopAdapter().apply {
            submitList(data)
        }
    }

    private inner class ShopAdapter
        : ListAdapter<LeftMenuData.Data.Shop, ShopAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemLeftMenuShopBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemLeftMenuShopBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: LeftMenuData.Data.Shop) = with(binding) {
                tvShop.text = data.name
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).centerCrop().into(ivShop)
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<LeftMenuData.Data.Shop>() {
        override fun areItemsTheSame(oldItem: LeftMenuData.Data.Shop, newItem: LeftMenuData.Data.Shop): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LeftMenuData.Data.Shop, newItem: LeftMenuData.Data.Shop): Boolean =
            oldItem == newItem
    }
}