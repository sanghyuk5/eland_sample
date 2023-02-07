package com.pionnet.eland.ui.goodsdetail.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewItemGoodsDetailOrderCustomerBinding
import com.pionnet.eland.databinding.ViewItemGoodsDetailRecommendBinding
import com.pionnet.eland.databinding.ViewItemGoodsDetailSellerRecommendBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.GoodsDetailData
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.priceFormat
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class GoodsDetailOrderStoreCustomerViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val dataList = it.toMutableList().checkItemsAre<GoodsDetailData.Data.StoreInfo>()
            dataList?.let { data ->
                initView(data)
            }
        }
    }

    private fun initView(data: List<GoodsDetailData.Data.StoreInfo>) = with(binding) {
        list.apply {
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            adapter = StoreCustomerAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class StoreCustomerAdapter
        : ListAdapter<GoodsDetailData.Data.StoreInfo, StoreCustomerAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailOrderCustomerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailOrderCustomerBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(data: GoodsDetailData.Data.StoreInfo) = with(binding) {
                when (data.title) {
                    "time" -> {
                        img.setImageResource(R.drawable.ic_baseline_access_time_24)
                    }
                    "mail" -> {
                        img.setImageResource(R.drawable.ic_baseline_alternate_email_24)
                    }
                    "call" -> {
                        img.setImageResource(R.drawable.ic_baseline_call_24)
                    }
                }

                name.text = data.data
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<GoodsDetailData.Data.StoreInfo>() {
        override fun areItemsTheSame(oldItem: GoodsDetailData.Data.StoreInfo, newItem: GoodsDetailData.Data.StoreInfo): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: GoodsDetailData.Data.StoreInfo, newItem: GoodsDetailData.Data.StoreInfo): Boolean =
            oldItem == newItem
    }
}