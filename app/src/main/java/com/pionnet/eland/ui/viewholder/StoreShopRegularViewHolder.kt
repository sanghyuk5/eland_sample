package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemStoreShopRecommendBinding
import com.pionnet.eland.databinding.ViewStoreShopRegularModuleBinding
import com.pionnet.eland.model.StoreShopData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp

class StoreShopRegularViewHolder(
    private val binding: ViewStoreShopRegularModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopRegularStoreData)?.let {
            initView(it.regularStoreData)
        }
    }

    private fun initView(data: List<StoreShopData.Data.Regular>) = with(binding) {
        viewTitle.tvTitle.text = "나의 단골매장"

        llNoRegular.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE

    }

    private inner class StoreShopRecommendAdapter
        : ListAdapter<StoreShopData.Data.Recommend, StoreShopRecommendAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemStoreShopRecommendBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemStoreShopRecommendBinding)
            : RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: StoreShopData.Data.Recommend) = with(binding) {
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).centerCrop().into(ivIcon)

            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<StoreShopData.Data.Recommend>() {
        override fun areItemsTheSame(oldItem: StoreShopData.Data.Recommend, newItem: StoreShopData.Data.Recommend): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: StoreShopData.Data.Recommend, newItem: StoreShopData.Data.Recommend): Boolean =
            oldItem == newItem
    }
}