package com.pionnet.eland.ui.search.searchBrand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pionnet.eland.databinding.*
import com.pionnet.eland.data.ViewType
import com.pionnet.eland.data.ViewTypeDataSet
import com.pionnet.eland.ui.goodsdetail.holder.GoodsDetailTitleViewHolder
import com.pionnet.eland.ui.main.ModuleDiffCallback
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.ui.viewholder.UnknownViewHolder

class SearchBrandAdapter(private val changeLetterCallback: (String) -> Unit)
    : ListAdapter<ViewTypeDataSet, BaseViewHolder>(ModuleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (ViewType.values()[viewType]) {
            ViewType.FIRST -> SearchBrandPopularViewHolder(
                ViewListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.SECOND -> SearchBrandLetterViewHolder(
                ViewSearchBrandLetterModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                changeLetterCallback
            )

            ViewType.THIRD -> SearchBrandLetterListViewHolder(
                ViewSearchBrandLetterListModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewType.FOURTH -> GoodsDetailTitleViewHolder(
                ViewCommonGoodsDetailTitleModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else ->  UnknownViewHolder(
                ViewUnknownModuleBinding.inflate(
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