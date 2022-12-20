package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewCommonWebViewModuleBinding
import com.pionnet.eland.model.PlanDetailData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.changedHeaderHtml

class CommonWebViewViewHolder(
    private val binding: ViewCommonWebViewModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonWebViewData)?.let {
            initView(it.shopInfo)
        }
    }

    private fun initView(data: PlanDetailData.Data.ShopInfo) = with(binding) {
        data.bannerInfo?.html?.let {
            //webView.loadUrl("https://m.naver.com")
            webView.loadData(changedHeaderHtml(it), "text/html; charset=utf-8", "UTF-8")
        }
    }
}