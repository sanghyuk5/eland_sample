package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewCommonSortModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class CommonSortViewHolder(
    private val binding: ViewCommonSortModuleBinding
) : BaseViewHolder(binding.root) {

    private var viewType = ""

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonSortData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonSortData) = with(binding) {
        viewType = data.viewType

        if (data.sortData != null) {
            name.text = data.sortData[data.sortPosition]
        } else {
            val sortData = root.context.resources.getStringArray(R.array.sort_list)
            name.text = sortData[data.sortPosition]
        }

        when (data.viewShape) {
            "grid" -> image.setImageResource(R.drawable.ic_baseline_grid_view_24)
            "linear" -> image.setImageResource(R.drawable.ic_baseline_menu_24)
            "large" -> image.setImageResource(R.drawable.ic_baseline_rectangle_24)
        }

        name.setOnClickListener {
            when (viewType) {
                "planDetail" -> EventBus.fire(HolderEvent(HolderEventType.PLAN_DETAIL_SORT, data.sortData ?: listOf<String>()))
                "storeShop" -> EventBus.fire(HolderEvent(HolderEventType.STORE_SHOP_SORT, data.sortData ?: listOf<String>()))
            }
        }

        sort.setOnClickListener {
            when (viewType) {
                "planDetail" -> EventBus.fire(HolderEvent(HolderEventType.PLAN_DETAIL_VIEW_CHANGE))
                "storeShop" -> EventBus.fire(HolderEvent(HolderEventType.STORE_SHOP_VIEW_CHANGE))
            }
        }
    }
}