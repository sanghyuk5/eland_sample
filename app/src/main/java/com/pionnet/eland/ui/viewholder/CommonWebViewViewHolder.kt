package com.pionnet.eland.ui.viewholder

import android.net.Uri
import com.pionnet.eland.databinding.ViewCommonWebViewModuleBinding
import com.pionnet.eland.model.PlanDetailData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.webview.ElandWebViewClient
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
            webView.setClientCallback(webViewCallback)
        }
    }

    private val webViewCallback = object : ElandWebViewClient.Callback {

        var isPageFinished = false

        override fun onPageStarted(url: String) {
            super.onPageStarted(url)
            isPageFinished = false
        }

        override fun onPageFinished(url: String) {
            super.onPageFinished(url)
            isPageFinished = true

        }

        override fun onSivScheme(url: String) {
            val uri = Uri.parse(url)
        }
    }
}