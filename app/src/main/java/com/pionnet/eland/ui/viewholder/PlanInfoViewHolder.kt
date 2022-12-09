package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewPlanInfoModuleBinding
import com.pionnet.eland.model.PlanDetailData
import com.pionnet.eland.ui.main.ModuleData

class PlanInfoViewHolder(
    private val binding: ViewPlanInfoModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.PlanInfoData)?.let {
            initView(it.shopInfo)
        }
    }

    private fun initView(data: PlanDetailData.Data.ShopInfo) = with(binding) {
        tvTitle.text = data.name
        data.bannerInfo?.html?.let {
            wvPlan.loadUrl("https://m.naver.com")
            //wvPlan.loadData(changedHeaderHtml(it), "text/html; charset=utf-8", "UTF-8")
        }
    }
}