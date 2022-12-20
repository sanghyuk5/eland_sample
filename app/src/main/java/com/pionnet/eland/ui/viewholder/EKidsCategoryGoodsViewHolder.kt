package com.pionnet.eland.ui.viewholder

import android.view.View
import com.pionnet.eland.databinding.ViewEkidsCategoryGoodsModuleBinding
import com.pionnet.eland.model.Goods
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
        GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(ivGood)
        tvBrand.text = data.brand
        tvContent.text = data.goodsName
        tvPercent.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        tvPer.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        tvPercent.text = data.saleRate.toString()
        tvPrice.visibility = if (data.marketPrice != null && data.marketPrice != 0) View.VISIBLE else View.INVISIBLE
        tvPrice.text = priceFormat(data.marketPrice ?: 0) + "원"
        tvSalePrice.text = priceFormat(data.salePrice ?: 0)
        tvCount.text = saleQuantity(data.saleQty ?: 0)
    }
}