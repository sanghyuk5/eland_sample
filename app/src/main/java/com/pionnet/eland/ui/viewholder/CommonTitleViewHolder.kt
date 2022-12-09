package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewCommonTitleModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class CommonTitleViewHolder(
    private val binding: ViewCommonTitleModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonTitleData) = with(binding) {
        tvTitle.text = data.title
        tvSubTitle.text = data.subTitle
    }
}