package com.pionnet.eland.ui.search.searchBrand

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewItemSearchBrandKeywordLetterListBinding
import com.pionnet.eland.databinding.ViewSearchBrandLetterListModuleBinding
import com.pionnet.eland.model.SearchBrandKeywordListData
import com.pionnet.eland.model.SearchBrandKeywordListData.Data.NavBrandKeyword
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre

class SearchBrandLetterListViewHolder(
    private val binding: ViewSearchBrandLetterListModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val keywordList = it.toMutableList().checkItemsAre<SearchBrandKeywordListData.Data>()
            keywordList?.let { it ->
                initView(it)
            }
        }
    }

    private fun initView(data: MutableList<SearchBrandKeywordListData.Data>) = with(binding) {
        result.text = "'" + data[0].navBrandKeywordTitle + "'" + "검색결과 총 " + data[0].navBrandKeywordList?.size.toString() + "건"

        list.adapter = SearchBrandLetterListAdapter().apply {
            submitList(data[0].navBrandKeywordList)
        }
    }

    private class SearchBrandLetterListAdapter
        : ListAdapter<NavBrandKeyword, SearchBrandLetterListAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemSearchBrandKeywordLetterListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        inner class ViewHolder(val binding: ViewItemSearchBrandKeywordLetterListBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: NavBrandKeyword) = with(binding) {
                rank.text = data.brandNm
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<NavBrandKeyword>() {
            override fun areItemsTheSame(oldItem: NavBrandKeyword, newItem: NavBrandKeyword): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NavBrandKeyword, newItem: NavBrandKeyword): Boolean =
                oldItem == newItem
        }
    }
}