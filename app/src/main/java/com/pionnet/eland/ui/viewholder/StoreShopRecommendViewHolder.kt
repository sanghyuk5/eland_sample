package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemStoreShopRecommendBinding
import com.pionnet.eland.databinding.ViewStoreShopRecommendModuleBinding
import com.pionnet.eland.model.StoreShopData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class StoreShopRecommendViewHolder(
    private val binding: ViewStoreShopRecommendModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopRecommendData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopRecommendData) = with(binding) {
        viewTitle.tvTitle.text = "추천 지점"
        rvRecommend.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 3.toPx, 3.toPx))
            adapter = StoreShopRecommendAdapter().apply {
                submitList(data.recommendData)
            }
        }
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
                tvName.text = data.storeNm
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