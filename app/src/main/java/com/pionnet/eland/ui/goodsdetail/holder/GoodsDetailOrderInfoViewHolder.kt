package com.pionnet.eland.ui.goodsdetail.holder

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.pionnet.eland.databinding.ViewCommonGoodsDetailTitleModuleBinding
import com.pionnet.eland.databinding.ViewGoodsDetailOrderInfoBinding
import com.pionnet.eland.model.GoodsDetailData
import com.pionnet.eland.ui.viewholder.BaseViewHolder

class GoodsDetailOrderInfoViewHolder(
    private val binding: ViewGoodsDetailOrderInfoBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? GoodsDetailData.Data.OrderInfoText)?.let {
            initView(it)
        }
    }

    private fun initView(data: GoodsDetailData.Data.OrderInfoText) = with(binding) {
        dot.visibility = if (data.isTitle) View.VISIBLE else View.GONE
        title.text = data.text
    }
}

