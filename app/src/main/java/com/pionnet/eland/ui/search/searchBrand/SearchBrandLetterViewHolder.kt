package com.pionnet.eland.ui.search.searchBrand

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewItemSearchBrandKeywordLetterBinding
import com.pionnet.eland.databinding.ViewSearchBrandLetterModuleBinding
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.GridMarginItemDecoration
import com.pionnet.eland.model.SearchBrandKeywordData.Data
import com.pionnet.eland.model.SearchBrandKeywordData.Data.NavBrandKeyword.NavBrandKeywordLetter
import com.pionnet.eland.ui.viewholder.ItemClickIntCallback

class SearchBrandLetterViewHolder(
    private val binding: ViewSearchBrandLetterModuleBinding,
    private var changeLetterCallback: (String) -> Unit
) : BaseViewHolder(binding.root) {

    private var letterType = 0
    private var keywordData: List<NavBrandKeywordLetter?>? = null

    private val itemClickIntCallback: ItemClickIntCallback = { index ->
        keywordData?.let {
            it.select(index) {
                keywordData = it.toMutableList()
            }
        }
        keywordData?.get(index)?.navBrandKeywordTitle?.let { keyword ->
            changeLetterCallback.invoke(keyword)
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val brandKeywordList = it.toMutableList().checkItemsAre<Data>()
            brandKeywordList?.let { it ->
                initView(it)
            }
        }
    }

    private fun initView(data: MutableList<Data>) = with(binding) {
        keywordData = data[0].navBrandKeyword?.navBrandKeywordListKor
        list.apply {
            if (itemDecorationCount == 0) addItemDecoration(GridMarginItemDecoration(6, 5.toPx))
            adapter = SearchBrandLetterAdapter(itemClickIntCallback).apply {
                submitList(keywordData)
            }
        }

        var keyword = "ㄱ"
        ivChange.setOnClickListener {
            if (letterType == 0) {
                letterType = 1
                keyword = "a"
            } else if (letterType == 1) {
                letterType = 0
                keyword = "ㄱ"
            }

            changeLetterCallback.invoke(keyword)

            keywordData = if (letterType == 0) data[0].navBrandKeyword?.navBrandKeywordListKor else data[0].navBrandKeyword?.navBrandKeywordListEng

            (list.adapter as? SearchBrandLetterAdapter)?.submitList(keywordData)
        }
    }

    private fun List<NavBrandKeywordLetter?>.select(index: Int, callback: (List<NavBrandKeywordLetter?>) -> Unit) {
        val data = this.map { it?.copy() }.toMutableList()
        val selectedItem = data.indexOfFirst { it!!.isSelected }
        if (selectedItem != -1 && selectedItem != index) {
            data.getOrNull(selectedItem)?.isSelected = false
            data.getOrNull(index)?.isSelected = true
            binding.list.apply {
                (adapter as? SearchBrandLetterAdapter)?.submitList(data)
            }

            callback.invoke(data)
        }
    }

    private class SearchBrandLetterAdapter(private val itemClickIntCallback: (Int) -> Unit) : ListAdapter<NavBrandKeywordLetter, SearchBrandLetterAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemSearchBrandKeywordLetterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
            holder.itemView.setOnClickListener {
                itemClickIntCallback.invoke(position)
            }
        }

        private inner class ViewHolder(val binding: ViewItemSearchBrandKeywordLetterBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: NavBrandKeywordLetter) = with(binding) {
                letter.text = data.navBrandKeywordTitle
                if (data.isSelected) {
                    layout.strokeColor = Color.parseColor("#c9000b")
                } else {
                    layout.strokeColor = Color.parseColor("#f4f5f7")
                }
            }
        }

        private class DiffCallback : DiffUtil.ItemCallback<NavBrandKeywordLetter>() {
            override fun areItemsTheSame(oldItem: NavBrandKeywordLetter, newItem: NavBrandKeywordLetter): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NavBrandKeywordLetter, newItem: NavBrandKeywordLetter): Boolean =
                oldItem == newItem
        }
    }
}
