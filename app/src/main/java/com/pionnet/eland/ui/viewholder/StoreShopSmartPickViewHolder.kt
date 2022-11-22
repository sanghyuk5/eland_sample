package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemCommonGoodBinding
import com.pionnet.eland.databinding.ViewItemMdRecommendGoodBinding
import com.pionnet.eland.databinding.ViewItemStoreShopRecommendBinding
import com.pionnet.eland.databinding.ViewStoreShopSmartPickModuleBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.StoreShopData
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat

class StoreShopSmartPickViewHolder(
    private val binding: ViewStoreShopSmartPickModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopSmartPickData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopSmartPickData) = with(binding) {
        viewTitle.tvTitle.text = "스토어픽 지점"
        viewTitle.tvSubTitle.text = "매장에서 직접 확인하고 픽업해보세요."

        tvStore.text = data.smartPickData[0].name

        tvSearch.setOnClickListener {

        }

        rvSmartPick.apply {
            adapter = SmartPickAdapter().apply {
                submitList(data.smartPickGoodData)
            }
        }

        tvTotal.text = data.smartPickData[0].name + " 전체상품보기 >"
        tvTotal.setOnClickListener {

        }
    }

    private inner class SmartPickAdapter
        : ListAdapter<Goods, SmartPickAdapter.ViewHolder>(GoodsDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemCommonGoodBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemCommonGoodBinding)
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
            }
        }
    }
}