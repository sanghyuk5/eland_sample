package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewHomeSeasonPlanModuleBinding
import com.pionnet.eland.databinding.ViewItemSeasonPlanBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.priceFormat
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class HomeSeasonPlanViewHolder(
    private val binding: ViewHomeSeasonPlanModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeSeasonPlansData)?.let {
            initView(it.homeSeasonPlansData)
        }
    }

    private fun initView(data: HomeData.Data.SeasonPlan.HomeOffer) = with(binding) {
        GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)

        list.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 3.toPx, 3.toPx))
            adapter = HomeSeasonPlanAdapter().apply {
                submitList(data.goodsList)
            }
        }
    }

    private inner class HomeSeasonPlanAdapter
        : ListAdapter<Goods, HomeSeasonPlanAdapter.ViewHolder>(GoodsDiffCallback()) {

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
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)
                brandName.text = data.brandName
                goodsName.text = data.goodsName
                priceAfter.text = priceFormat(data.salePrice ?: 0)
            }
        }
    }
}