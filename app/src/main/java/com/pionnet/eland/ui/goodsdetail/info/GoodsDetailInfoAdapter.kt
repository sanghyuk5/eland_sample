package com.pionnet.eland.ui.goodsdetail.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pionnet.eland.databinding.*
import com.pionnet.eland.data.ViewType
import com.pionnet.eland.data.ViewTypeDataSet
import com.pionnet.eland.ui.goodsdetail.holder.*
import com.pionnet.eland.ui.main.ModuleDiffCallback
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.ui.viewholder.CommonDividerViewHolder

class GoodsDetailInfoAdapter: ListAdapter<ViewTypeDataSet, BaseViewHolder>(ModuleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (ViewType.values()[viewType]) {
            ViewType.FIRST -> GoodsDetailWebViewViewHolder(
                ViewGoodsDetailWebViewModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.SECOND -> GoodsDetailSellerRecommendViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.THIRD -> GoodsDetailTagViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.FOURTH -> GoodsDetailSellerPopularViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.FIFTH, ViewType.SIXTH -> GoodsDetailRecommendViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.SEVENTH -> GoodsDetailTitleViewHolder(
                ViewCommonGoodsDetailTitleModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> CommonDividerViewHolder(
                ViewCommonDividerModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position).data ?: Any()
        holder.onBind(item, position)
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal
}