package com.pionnet.eland.ui.viewholder

import android.view.View
import com.pionnet.eland.databinding.ViewCommonGoodsLinearModuleBinding
import com.pionnet.eland.data.Goods
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.FlagUtil
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat

class CommonGoodsLinearViewHolder(
    private val binding: ViewCommonGoodsLinearModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonGoodsLinearData)?.let {
            initView(it.goodsData)
        }
    }

    private fun initView(data: Goods) = with(binding) {
        GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)
        brandName.text = data.brandName
        goodsName.text = data.goodsName
        saleRate.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        tvPer.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        saleRate.text = data.saleRate.toString()
        priceBefore.visibility = if (data.marketPrice != null && data.marketPrice != 0) View.VISIBLE else View.INVISIBLE
        priceBefore.text = priceFormat(data.marketPrice ?: 0) + "원"
        priceAfter.text = priceFormat(data.salePrice ?: 0)
        ratingbar.visibility = if (data.starPoint != null) View.VISIBLE else View.INVISIBLE
        ratingbar.rating = ((data.starPoint ?: 0)/20).toFloat()
        reply.visibility = if (data.commentCnt != null) View.VISIBLE else View.GONE
        reply.text = "리뷰(" + data.commentCnt.toString() + ")"
        flag.viewType = "goods"
        flag.visibility = if (!data.iconView.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
        flag.flags = FlagUtil.from(data.iconView)
    }
}