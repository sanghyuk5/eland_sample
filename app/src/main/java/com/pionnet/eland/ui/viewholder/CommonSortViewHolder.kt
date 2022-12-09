package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.EventBus
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewCommonSortModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class CommonSortViewHolder(
    private val binding: ViewCommonSortModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonSortData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonSortData) = with(binding) {
        if (data.sortData != null) {
            tvSort.text = data.sortData[data.sortPosition]
        } else {
            val sortData = root.context.resources.getStringArray(R.array.sort_list)
            tvSort.text = sortData[data.sortPosition]
        }

        when (data.viewType) {
            "grid" -> ivSort.setImageResource(R.drawable.ic_baseline_grid_view_24)
            "linear" -> ivSort.setImageResource(R.drawable.ic_baseline_menu_24)
            "large" -> ivSort.setImageResource(R.drawable.ic_baseline_rectangle_24)
        }

        tvSort.setOnClickListener {
            EventBus.fire(data.sortData ?: mutableListOf())
        }

        ivSort.setOnClickListener {
            EventBus.fire("viewChange")
        }
    }
}