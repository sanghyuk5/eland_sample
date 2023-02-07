package com.pionnet.eland.ui.goodsdetail.holder

import com.orhanobut.logger.Logger
import com.pionnet.eland.databinding.ViewGoodsDetailWebViewModuleBinding
import com.pionnet.eland.ui.viewholder.BaseViewHolder

class GoodsDetailWebViewViewHolder(
    private val binding: ViewGoodsDetailWebViewModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? String)?.let {
            initView(it)
        }
    }

    private fun initView(data: String) = with(binding) {
        Logger.d("hyuk gang")
        webView.loadUrl(data)
    }
}