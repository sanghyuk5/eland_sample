package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.EventBus
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewStoreShopPickModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class StoreShopPickViewHolder(
    private val binding: ViewStoreShopPickModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopSmartPickData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopSmartPickData) = with(binding) {
        viewTitle.tvTitle.text = "스토어픽 지점"
        viewTitle.tvSubTitle.text = "매장에서 직접 확인하고 픽업해보세요."

        tvStore.text = data.pickName

        val sortData = root.context.resources.getStringArray(R.array.sort_list)
        tvSort.text = sortData[data.sort]

        when (data.viewType) {
            "grid" -> ivSort.setImageResource(R.drawable.ic_baseline_grid_view_24)
            "linear" -> ivSort.setImageResource(R.drawable.ic_baseline_menu_24)
            "large" -> ivSort.setImageResource(R.drawable.ic_baseline_rectangle_24)
        }

        tvSearch.setOnClickListener {
            EventBus.fire(data)
        }

        tvSort.setOnClickListener {
            EventBus.fire("sort")
        }

        ivSort.setOnClickListener {
            EventBus.fire("viewChange")
        }
    }
}