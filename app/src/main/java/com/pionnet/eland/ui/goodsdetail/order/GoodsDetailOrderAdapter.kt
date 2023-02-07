package com.pionnet.eland.ui.goodsdetail.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pionnet.eland.databinding.*
import com.pionnet.eland.model.ViewType
import com.pionnet.eland.model.ViewTypeDataSet
import com.pionnet.eland.ui.goodsdetail.holder.*
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.ui.viewholder.CommonDividerViewHolder

class GoodsDetailOrderAdapter: ListAdapter<ViewTypeDataSet, BaseViewHolder>(moduleDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (ViewType.values()[viewType]) {
            ViewType.FIRST -> GoodsDetailOrderInfoViewHolder(
                ViewGoodsDetailOrderInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.SECOND -> CommonDividerViewHolder(
                ViewCommonDividerModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.THIRD -> GoodsDetailTitleViewHolder(
                ViewCommonGoodsDetailTitleModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.FOURTH -> GoodsDetailOrderStoreInfoViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.FIFTH -> GoodsDetailOrderStoreInfoViewHolder(
                ViewListBinding.inflate(
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

    companion object {
        private val moduleDiffUtilCallback = object : DiffUtil.ItemCallback<ViewTypeDataSet>() {
            override fun areItemsTheSame(oldItem: ViewTypeDataSet, newItem: ViewTypeDataSet): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: ViewTypeDataSet, newItem: ViewTypeDataSet): Boolean = oldItem == newItem
        }
    }
}