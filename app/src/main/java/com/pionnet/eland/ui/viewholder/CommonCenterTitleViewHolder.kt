package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewCommonCenterTitleModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class CommonCenterTitleViewHolder(
    private val binding: ViewCommonCenterTitleModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonCenterTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonCenterTitleData) = with(binding) {
        tvTitle.text = data.title
    }
}