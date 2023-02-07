package com.pionnet.eland.ui.goodsdetail.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemGoodsDetailRecommendBinding
import com.pionnet.eland.databinding.ViewItemGoodsDetailSellerRecommendBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.Goods
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.priceFormat
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class GoodsDetailRecommendViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val dataList = it.toMutableList().checkItemsAre<Goods>()
            dataList?.let { data ->
                initView(data)
            }
        }
    }

    private fun initView(data: MutableList<Goods>) = with(binding) {
        list.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 7.toPx, 7.toPx))
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GoodsAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class GoodsAdapter
        : ListAdapter<Goods, GoodsAdapter.ViewHolder>(GoodsDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailRecommendBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailRecommendBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: Goods) = with(binding) {
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(goodsImg)
                brandNm.text = data.brandName
                goodsNm.text = data.goodsName
                saleRate.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
                saleRate.text = data.saleRate.toString() + "%"
                priceBefore.visibility = if (data.marketPrice != null && data.marketPrice != 0) View.VISIBLE else View.INVISIBLE
                priceBefore.text = priceFormat(data.marketPrice ?: 0)
                priceAfter.text = priceFormat(data.salePrice ?: 0)
            }
        }
    }
}