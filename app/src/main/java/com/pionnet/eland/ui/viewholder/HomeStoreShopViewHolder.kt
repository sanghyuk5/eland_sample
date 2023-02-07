package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewHomeStoreShopModuleBinding
import com.pionnet.eland.databinding.ViewItemBannerBinding
import com.pionnet.eland.databinding.ViewItemSeasonPlanBinding
import com.pionnet.eland.model.Banner
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.ui.main.BannerDiffCallback
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat

class HomeStoreShopViewHolder(
    private val binding: ViewHomeStoreShopModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeStoreShopData)?.let {
            initView(it.homeStoreShopData)
        }
    }

    private fun initView(data: HomeData.Data.StoreShop) = with(binding) {
        viewTitle.tvTitle.text = data.title

        rvBanner.apply {
            adapter = HomeStoreShopBannerAdapter().apply {
                submitList(data.bannerList)
            }
        }

        rvGoods.apply {
            adapter = HomeStoreShopGoodsAdapter().apply {
                submitList(data.goodsList)
            }
        }
    }

    private inner class HomeStoreShopBannerAdapter
        : ListAdapter<Banner, HomeStoreShopBannerAdapter.ViewHolder>(BannerDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemBannerBinding)
            : RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: Banner) = with(binding) {
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(ivBanner)
            }
        }
    }

    private inner class HomeStoreShopGoodsAdapter
        : ListAdapter<Goods, HomeStoreShopGoodsAdapter.ViewHolder>(GoodsDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemSeasonPlanBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemSeasonPlanBinding)
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
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(ivSeasonPlan)
                brandName.text = data.brandName
                goodsName.text = data.goodsName
                priceAfter.text = priceFormat(data.salePrice ?: 0)
            }
        }
    }
}