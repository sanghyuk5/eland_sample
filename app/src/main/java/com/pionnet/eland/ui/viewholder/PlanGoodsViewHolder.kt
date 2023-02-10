package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewPlanGoodsModuleBinding
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp

class PlanGoodsViewHolder(
    private val binding: ViewPlanGoodsModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.PlanGoodsData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.PlanGoodsData) = with(binding) {
        GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)

        list.adapter = CommonGoodsHorizontalViewHolder.HorizontalAdapter().apply {
            submitList(data.goods)
        }
    }
}