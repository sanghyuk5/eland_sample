package com.pionnet.eland.ui.goodsdetail.holder

import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewGoodsDetailReviewMoreModuleBinding
import com.pionnet.eland.ui.viewholder.BaseViewHolder

class GoodsDetailReviewMoreViewHolder(
    private val binding: ViewGoodsDetailReviewMoreModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? String)?.let {
            initView(it)
        }
    }

    private fun initView(data: String) = with(binding) {
        root.setOnClickListener {
            EventBus.fire(LinkEvent(data))
        }
    }
}