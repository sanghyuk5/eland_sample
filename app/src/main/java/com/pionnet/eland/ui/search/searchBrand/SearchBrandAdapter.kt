package com.pionnet.eland.ui.search.searchBrand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pionnet.eland.databinding.*
import com.pionnet.eland.ui.viewholder.BaseViewHolder

class SearchBrandAdapter(private val changeLetterCallback: (String) -> Unit)
    : ListAdapter<SearchBrandDataSet, BaseViewHolder>(moduleDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (SearchBrandViewType.values()[viewType]) {
            SearchBrandViewType.POPULAR -> SearchBrandPopularViewHolder(
                ViewSearchBrandPopularModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            SearchBrandViewType.LETTER -> SearchBrandLetterViewHolder(
                ViewSearchBrandLetterModuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                changeLetterCallback
            )

            SearchBrandViewType.LETTERLIST -> SearchBrandLetterListViewHolder(
                ViewSearchBrandLetterListModuleBinding.inflate(
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
        private val moduleDiffUtilCallback = object : DiffUtil.ItemCallback<SearchBrandDataSet>() {
            override fun areItemsTheSame(oldItem: SearchBrandDataSet, newItem: SearchBrandDataSet): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: SearchBrandDataSet, newItem: SearchBrandDataSet): Boolean = oldItem == newItem
        }
    }
}