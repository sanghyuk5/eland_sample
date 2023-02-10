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
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonGoodsGridData,) = with(binding) {
        if (data.goodsData.getOrNull(0)?.imageUrl.isNullOrEmpty()) viewLeftGood.root.visibility = View.INVISIBLE
        else {
            viewLeftGood.root.visibility = View.VISIBLE
            viewLeftGood.setView(data.viewType, data.goodsData[0], data.index * 2) { pickGoodsNo ->

            }
        }

        if (data.goodsData.getOrNull(1)?.imageUrl.isNullOrEmpty()) viewRightGood.root.visibility = View.INVISIBLE
        else {
            viewRightGood.root.visibility = View.VISIBLE
            viewRightGood.setView(data.viewType, data.goodsData[1], data.index * 2 + 1) { pickGoodsNo ->

            }
        }
    }

    private fun ViewItemCommonGoodBinding.setView(viewType: String, data: Goods, position: Int, Callback: (String?) -> Unit) {
        GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)

        brandName.text = data.brandName
        goodsName.text = data.goodsName
        saleRate.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        per.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
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

        if (viewType == "best") {
            rank.visibility = View.VISIBLE
            rank.text = (position + 1).toString()
            if (position + 1 <= 3) rank.setBackgroundResource(R.drawable.ic_baseline_rectangle_red_24)
            else if (position + 1 <= 20) rank.setBackgroundResource(R.drawable.ic_baseline_rectangle_orange_24)
            else rank.visibility = View.GONE
        } else {
            rank.visibility = View.GONE
        }
    }
}