package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewPlanTabTitleModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class PlanTabTitleViewHolder(
    private val binding: ViewPlanTabTitleModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.PlanTabTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.PlanTabTitleData) = with(binding) {
        tvTitle.text = data.tabTitle + "(${data.count})"
    }
}