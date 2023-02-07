package com.pionnet.eland.ui.leftmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pionnet.eland.databinding.*
import com.pionnet.eland.model.LeftMenuDataSet
import com.pionnet.eland.model.LeftMenuViewType
import com.pionnet.eland.ui.leftmenu.holder.*
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.ui.viewholder.CommonDividerViewHolder

class LeftMenuAdapter(private val logoutClickCallback: () -> Unit)
    : ListAdapter<LeftMenuDataSet, BaseViewHolder>(moduleDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (LeftMenuViewType.values()[viewType]) {
            LeftMenuViewType.RECENT -> LeftMenuRecentlyViewHolder(
                ViewLeftMenuRecentlyModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            LeftMenuViewType.CATEGORY -> LeftMenuCategoryViewHolder(
                ViewLeftMenuCategoryModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            LeftMenuViewType.BRAND -> LeftMenuBrandViewHolder(
                ViewLeftMenuBrandModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            LeftMenuViewType.SHOP -> LeftMenuShopViewHolder(
                ViewLeftMenuShopModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            LeftMenuViewType.SERVICE -> LeftMenuServiceMenuViewHolder(
                ViewLeftMenuServiceModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            LeftMenuViewType.BOTTOM -> LeftMenuLogoutViewHolder(
                ViewLeftMenuLogoutModuleBinding.inflate(
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
        private val moduleDiffUtilCallback = object : DiffUtil.ItemCallback<LeftMenuDataSet>() {
            override fun areItemsTheSame(oldItem: LeftMenuDataSet, newItem: LeftMenuDataSet): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: LeftMenuDataSet, newItem: LeftMenuDataSet): Boolean = oldItem == newItem
        }
    }

}