package com.pionnet.eland.ui.viewholder

import android.view.View
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewCommonGoodsGridModuleBinding
import com.pionnet.eland.databinding.ViewItemCommonGoodBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.FlagUtil
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat

class CommonGoodsGridViewHolder(
    private val binding: ViewCommonGoodsGridModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonGoodsGridData)?.let {
            initView(it, position)
        }
    }

    private fun initView(data: ModuleData.CommonGoodsGridData, position: Int) = with(binding) {
        if (data.goodsData.getOrNull(0)?.imageUrl.isNullOrEmpty()) viewLeftGood.root.visibility = View.INVISIBLE
        else {
            viewLeftGood.root.visibility = View.VISIBLE
            viewLeftGood.setView(data.viewType, data.goodsData[0], position * 2) { pickGoodsNo ->

            }
        }

        if (data.goodsData.getOrNull(1)?.imageUrl.isNullOrEmpty()) viewRightGood.root.visibility = View.INVISIBLE
        else {
            viewRightGood.root.visibility = View.VISIBLE
            viewRightGood.setView(data.viewType, data.goodsData[1], position * 2 + 1) { pickGoodsNo ->

            }
        }
    }

    private fun ViewItemCommonGoodBinding.setView(viewType: String, data: Goods, position: Int, Callback: (String?) -> Unit) {
        GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(ivGood)

        tvBrand.text = data.brand
        tvContent.text = data.goodsName
        tvPercent.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        tvPer.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        tvPercent.text = data.saleRate.toString()
        tvPrice.visibility = if (data.marketPrice != null && data.marketPrice != 0) View.VISIBLE else View.INVISIBLE
        tvPrice.text = priceFormat(data.marketPrice ?: 0) + "원"
        tvSalePrice.text = priceFormat(data.salePrice ?: 0)
        ratingbar.visibility = if (data.starPoint != null) View.VISIBLE else View.INVISIBLE
        ratingbar.rating = ((data.starPoint ?: 0)/20).toFloat()
        tvReply.visibility = if (data.commentCnt != null) View.VISIBLE else View.GONE
        tvReply.text = "리뷰(" + data.commentCnt.toString() + ")"
        cfvFlag.viewType = "goods"
        cfvFlag.visibility = if (!data.iconView.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
        cfvFlag.flags = FlagUtil.from(data.iconView)

        if (viewType == "best") {
            tvRank.visibility = View.VISIBLE
            tvRank.text = (position + 1).toString()
            if (position + 1 <= 3) tvRank.setBackgroundResource(R.drawable.ic_baseline_rectangle_red_24)
            else if (position + 1 <= 20) tvRank.setBackgroundResource(R.drawable.ic_baseline_rectangle_orange_24)
            else tvRank.visibility = View.GONE
        } else {
            tvRank.visibility = View.GONE
        }
    }
}