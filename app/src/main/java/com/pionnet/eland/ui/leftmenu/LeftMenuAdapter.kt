package com.pionnet.eland.ui.leftmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pionnet.eland.databinding.*
import com.pionnet.eland.model.ViewType
import com.pionnet.eland.model.ViewTypeDataSet
import com.pionnet.eland.ui.goodsdetail.holder.GoodsDetailTitleViewHolder
import com.pionnet.eland.ui.leftmenu.holder.*
import com.pionnet.eland.ui.main.ModuleDiffCallback
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.ui.viewholder.CommonDividerViewHolder
import com.pionnet.eland.ui.viewholder.CommonTitleViewHolder

class LeftMenuAdapter(private val logoutClickCallback: () -> Unit)
    : ListAdapter<ViewTypeDataSet, BaseViewHolder>(ModuleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (ViewType.values()[viewType]) {
            ViewType.FIRST -> LeftMenuRecentlyViewHolder(
                ViewLeftMenuRecentlyModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.SECOND -> LeftMenuCategoryViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.THIRD -> LeftMenuBrandViewHolder(
                ViewLeftMenuBrandModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.FOURTH -> LeftMenuShopViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.FIFTH -> LeftMenuServiceMenuViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.SIXTH -> LeftMenuLogoutViewHolder(
                ViewLeftMenuLogoutModuleBinding.inflate(
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