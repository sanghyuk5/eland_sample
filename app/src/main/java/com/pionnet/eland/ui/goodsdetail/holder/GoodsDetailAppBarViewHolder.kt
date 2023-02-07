package com.pionnet.eland.ui.goodsdetail.holder

import com.pionnet.eland.databinding.ViewGoodsDetailAppBarBinding
import com.pionnet.eland.ui.viewholder.BaseViewHolder

class GoodsDetailAppBarViewHolder(
    private val binding: ViewGoodsDetailAppBarBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        initView()
    }

    private fun initView() = with(binding) {
        ivBack.setOnClickListener {  }
        ivHome.setOnClickListener {  }
        ivSearch.setOnClickListener {  }
        ivCart.setOnClickListener {  }
    }
}