package com.pionnet.eland.ui.viewholder

import android.view.View
import com.bumptech.glide.request.target.Target
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewCommonLuckyDealGoodsModuleBinding
import com.pionnet.eland.data.Goods
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
            .into(AdjustHeightImageViewTarget(image))

        GlideApp.with(itemView.context).load("https:" + data.flagImage).into(thumbnail)

        brandName.visibility = if (!data.brandName.isNullOrEmpty()) View.VISIBLE else View.GONE
        brandName.text = data.brandName

        goodsName.text = data.goodsName

        if (data.saleRate != null && data.saleRate != 0) {
            saleRate.visibility = View.VISIBLE
            tvPer.visibility = View.VISIBLE
            saleRate.text = data.saleRate.toString()
        } else {
            saleRate.visibility = View.GONE
            tvPer.visibility = View.GONE
        }

        priceAfter.text = priceFormat(data.salePrice ?: 0)

        if (data.marketPrice.toString().isEmpty() || data.marketPrice.toString() == "0") {
            priceBefore.visibility = View.GONE
        } else {
            priceBefore.visibility = View.VISIBLE
            priceBefore.text = priceFormat(data.marketPrice ?: 0)
        }

        flag.visibility = if (!data.iconView.isNullOrEmpty()) View.VISIBLE else View.GONE
        flag.flags = FlagUtil.from(data.iconView)

        count.text = saleQuantity(data.saleQty ?: 0)
    }
}