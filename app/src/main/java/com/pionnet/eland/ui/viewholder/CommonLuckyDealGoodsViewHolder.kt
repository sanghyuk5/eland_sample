package com.pionnet.eland.ui.viewholder

import android.view.View
import com.bumptech.glide.request.target.Target
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewCommonLuckyDealGoodsModuleBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.AdjustHeightImageViewTarget
import com.pionnet.eland.utils.FlagUtil
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat
import com.pionnet.eland.utils.saleQuantity

class CommonLuckyDealGoodsViewHolder(
    private val binding: ViewCommonLuckyDealGoodsModuleBinding
) : BaseViewHolder(binding.root) {

    private var linkUrl: String? = null

    init {
        binding.root.setOnClickListener {
            linkUrl?.let {
                EventBus.fire(LinkEvent(it))
            }
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonLuckyDealGoods)?.let {
            initView(it.goodsData)
        }
    }

    private fun initView(data: Goods) = with(binding) {
        linkUrl = data.linkUrl

        GlideApp.with(itemView.context)
            .load("https:" + data.imageUrl)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .placeholder(R.drawable.ic_baseline_wifi_tethering_24)
            .into(AdjustHeightImageViewTarget(ivLucky))

        tvBrand.visibility = if (!data.brand.isNullOrEmpty()) View.VISIBLE else View.GONE
        tvBrand.text = data.brand

        tvContent.text = data.goodsName

        if (data.saleRate != null && data.saleRate != 0) {
            tvPercent.visibility = View.VISIBLE
            tvPer.visibility = View.VISIBLE
            tvPercent.text = data.saleRate.toString()
        } else {
            tvPercent.visibility = View.GONE
            tvPer.visibility = View.GONE
        }

        tvSalePrice.text = priceFormat(data.salePrice ?: 0)

        if (data.marketPrice.toString().isEmpty() || data.marketPrice.toString() == "0") {
            tvPrice.visibility = View.GONE
        } else {
            tvPrice.visibility = View.VISIBLE
            tvPrice.text = priceFormat(data.marketPrice ?: 0)
        }

        cfvFlag.visibility = if (!data.iconView.isNullOrEmpty()) View.VISIBLE else View.GONE
        cfvFlag.flags = FlagUtil.from(data.iconView)

        tvCount.text = saleQuantity(data.saleQty ?: 0)
    }
}