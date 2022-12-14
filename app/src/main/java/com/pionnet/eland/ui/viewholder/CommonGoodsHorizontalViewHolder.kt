package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewCommonGoodsHorizontalModuleBinding
import com.pionnet.eland.databinding.ViewItemHorizontalGoodBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat

class CommonGoodsHorizontalViewHolder(
    private val binding: ViewCommonGoodsHorizontalModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonGoodsHorizontalData)?.let {
            initView(it.goodsData)
        }
    }

    private fun initView(data: List<Goods>) = with(binding) {
        rvGoods.apply {
            if (adapter == null) {
                adapter = HorizontalAdapter().apply {
                    submitList(data)
                }
            }
        }
    }

    class HorizontalAdapter
        : ListAdapter<Goods, HorizontalAdapter.ViewHolder>(GoodsDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemHorizontalGoodBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        inner class ViewHolder(val binding: ViewItemHorizontalGoodBinding)
            : RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: Goods) = with(binding) {
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(ivMdGood)
                tvBrand.text = data.brand
                tvContent.text = data.goodsName
                tvPercent.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
                tvPer.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
                tvPercent.text = data.saleRate.toString()
                tvPrice.visibility = if (data.marketPrice != null && data.marketPrice != 0) View.VISIBLE else View.INVISIBLE
                tvPrice.text = priceFormat(data.marketPrice ?: 0) + "???"
                tvSalePrice.text = priceFormat(data.salePrice ?: 0)
                ratingbar.visibility = if (data.starPoint != null) View.VISIBLE else View.INVISIBLE
                ratingbar.rating = ((data.starPoint ?: 0)/20).toFloat()
                tvReply.visibility = if (data.commentCnt != null) View.VISIBLE else View.GONE
                tvReply.text = "??????(" + data.commentCnt.toString() + ")"
            }
        }
    }
}