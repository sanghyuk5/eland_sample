package com.pionnet.eland.ui.viewholder

import android.view.View
import com.pionnet.eland.databinding.ViewEkidsCategoryGoodsModuleBinding
import com.pionnet.eland.data.Goods
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat
import com.pionnet.eland.utils.saleQuantity

class EKidsCategoryGoodsViewHolder(
    private val binding: ViewEkidsCategoryGoodsModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.EKidsCategoryGoodsData)?.let {
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
        count.text = saleQuantity(data.saleQty ?: 0)
    }
}