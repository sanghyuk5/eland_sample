package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewPlanDetailTabTitleModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class PlanDetailTabTitleViewHolder(
    private val binding: ViewPlanDetailTabTitleModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.PlanTabTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.PlanTabTitleData) = with(binding) {
        title.text = data.tabTitle + "(${data.count})"
    }
}