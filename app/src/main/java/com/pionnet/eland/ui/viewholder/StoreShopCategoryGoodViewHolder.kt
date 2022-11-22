package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemMdRecommendGoodBinding
import com.pionnet.eland.databinding.ViewStoreShopCategoryGoodModuleBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat

class StoreShopCategoryGoodViewHolder(
    private val binding: ViewStoreShopCategoryGoodModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopCategoryGoodData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopCategoryGoodData) = with(binding) {
        rvCategoryGoods.apply {
            adapter = CategoryGoodsAdapter().apply {
                submitList(data.goodData)
            }
        }

    }

    private inner class CategoryGoodsAdapter
        : ListAdapter<Goods, CategoryGoodsAdapter.ViewHolder>(GoodsDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemMdRecommendGoodBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemMdRecommendGoodBinding)
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
                tvPrice.text = priceFormat(data.marketPrice ?: 0)
                tvSalePrice.text = priceFormat(data.salePrice ?: 0)
                ratingbar.rating = ((data.starPoint ?: 0)/20).toFloat()
                tvReply.text = "리뷰(" + data.commentCnt.toString() + ")"
            }
        }
    }
}