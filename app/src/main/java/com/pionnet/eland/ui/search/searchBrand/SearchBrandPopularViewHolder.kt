package com.pionnet.eland.ui.search.searchBrand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewItemSearchBrandPopularBinding
import com.pionnet.eland.databinding.ViewSearchBrandPopularModuleBinding
import com.pionnet.eland.model.SearchRank
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class SearchBrandPopularViewHolder(
    private val binding: ViewSearchBrandPopularModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val searchRank = it.toMutableList().checkItemsAre<SearchRank>()
            searchRank?.let { it ->
                initView(it)
            }
        }
    }

    private fun initView(data: MutableList<SearchRank>) = with(binding) {
        rvBrand.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(3.toPx, 5.toPx, 5.toPx))
            adapter = SearchBrandPopularAdapter().apply {
                submitList(data)
            }
        }
    }

    private class SearchBrandPopularAdapter
        : ListAdapter<SearchRank, SearchBrandPopularAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemSearchBrandPopularBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position], position)
        }

        inner class ViewHolder(val binding: ViewItemSearchBrandPopularBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: SearchRank, position: Int) = with(binding) {
                brandNamePopular.text = (position + 1).toString() + " " + data.keyword
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<SearchRank>() {
            override fun areItemsTheSame(oldItem: SearchRank, newItem: SearchRank): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: SearchRank, newItem: SearchRank): Boolean =
                oldItem == newItem
        }
    }
}