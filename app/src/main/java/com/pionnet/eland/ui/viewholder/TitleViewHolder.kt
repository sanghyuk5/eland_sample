package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewTitleModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class TitleViewHolder(
    private val binding: ViewTitleModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.HomeTitleData) = with(binding) {
        tvTitle.text = data.title
        tvSubTitle.text = data.subTitle
    }
}