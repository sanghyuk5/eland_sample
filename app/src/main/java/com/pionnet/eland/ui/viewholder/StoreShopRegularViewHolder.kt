package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.databinding.ViewItemStoreShopRegularMenuBinding
import com.pionnet.eland.databinding.ViewStoreShopRegularModuleBinding
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.main.StringDiffCallback

class StoreShopRegularViewHolder(
    private val binding: ViewStoreShopRegularModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopRegularStoreData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopRegularStoreData) = with(binding) {
        regularLayout.visibility = if (data.isShowData) View.VISIBLE else View.GONE
        noRegularLayout.visibility = if (data.isShowData) View.GONE else View.VISIBLE

        search.setOnClickListener {
            EventBus.fire(HolderEvent(HolderEventType.STORE_SHOP_REGULAR_SEARCH))
        }

        if (data.isShowData) { // 메뉴는 하드코딩된 정보로 그리기
            val menuList = mutableListOf<String>()
            menuList.add("선택한 지점")
            listMenu.adapter = StoreShopRegularMenuAdapter().apply {
                submitList(menuList)
            }

            listItem.adapter = CommonGoodsHorizontalViewHolder.HorizontalAdapter().apply {
                submitList(data.goods)
            }
        }
    }

    private inner class StoreShopRegularMenuAdapter
        : ListAdapter<String, StoreShopRegularMenuAdapter.ViewHolder>(StringDiffCallback()) {

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
                name.text = data
            }
        }
    }
}