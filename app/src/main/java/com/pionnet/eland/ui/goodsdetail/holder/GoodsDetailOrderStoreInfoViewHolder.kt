package com.pionnet.eland.ui.goodsdetail.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewItemGoodsDetailOrderStoreBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.data.GoodsDetailData
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre

class GoodsDetailOrderStoreInfoViewHolder(
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
            adapter = StoreInfoAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class StoreInfoAdapter
        : ListAdapter<GoodsDetailData.Data.StoreInfo, StoreInfoAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailOrderStoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailOrderStoreBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(data: GoodsDetailData.Data.StoreInfo) = with(binding) {
                title.text = data.title
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