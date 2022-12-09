package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.databinding.ViewItemStoreShopRegularMenuBinding
import com.pionnet.eland.databinding.ViewStoreShopRegularModuleBinding
import com.pionnet.eland.ui.main.HorizontalAdapter
import com.pionnet.eland.ui.main.ModuleData

class StoreShopRegularViewHolder(
    private val binding: ViewStoreShopRegularModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopRegularStoreData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopRegularStoreData) = with(binding) {
        llRegular.visibility = if (data.isShowData) View.VISIBLE else View.GONE
        llNoRegular.visibility = if (data.isShowData) View.GONE else View.VISIBLE

        llSearch.setOnClickListener {
            EventBus.fire("searchStore")
        }

        if (data.isShowData) { // 메뉴는 하드코딩된 정보로 그리기
            val menuList = mutableListOf<String>()
            menuList.add("선택한 지점")
            rvRegularMenu.adapter = StoreShopRegularMenuAdapter().apply {
                submitList(menuList)
            }

            rvRegularItem.adapter = HorizontalAdapter().apply {
                submitList(data.goods)
            }
        }
    }

    private inner class StoreShopRegularMenuAdapter
        : ListAdapter<String, StoreShopRegularMenuAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemStoreShopRegularMenuBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemStoreShopRegularMenuBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: String) = with(binding) {
                tvMenu.text = data
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}