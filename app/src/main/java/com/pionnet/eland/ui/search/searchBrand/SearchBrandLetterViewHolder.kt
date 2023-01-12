package com.pionnet.eland.ui.search.searchBrand

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.pionnet.eland.databinding.ViewItemSearchBrandKeywordLetterBinding
import com.pionnet.eland.databinding.ViewSearchBrandLetterModuleBinding
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.GridMarginItemDecoration
import com.pionnet.eland.model.SearchBrandKeyword.Data
import com.pionnet.eland.model.SearchBrandKeyword.Data.NavBrandKeyword.NavBrandKeywordLetter

class SearchBrandLetterViewHolder(
    private val binding: ViewSearchBrandLetterModuleBinding,
    private val changeLetterCallback: () -> Unit
) : BaseViewHolder(binding.root) {

    private var letterType = 0

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val brandKeywordList = it.toMutableList().checkItemsAre<Data>()
            brandKeywordList?.let { it ->
                initView(it)
            }
        }
    }

    private fun initView(data: MutableList<Data>) = with(binding) {
        rvLetter.apply {
            if (itemDecorationCount == 0) addItemDecoration(GridMarginItemDecoration(6, 5.toPx))
            adapter = SearchBrandLetterAdapter().apply {
                submitList(data[0].navBrandKeyword?.navBrandKeywordListKor)
            }
        }

        ivChange.setOnClickListener {
            if (letterType == 0) {
                letterType = 1
            } else if (letterType == 1) {
                letterType = 0
            }

            changeLetterCallback.invoke()

            val letter = if (letterType == 0) data[0].navBrandKeyword?.navBrandKeywordListKor else data[0].navBrandKeyword?.navBrandKeywordListEng

            (rvLetter.adapter as? SearchBrandLetterAdapter)?.submitList(letter)
        }
    }

    private class SearchBrandLetterAdapter
        : ListAdapter<NavBrandKeywordLetter, SearchBrandLetterAdapter.ViewHolder>(DiffCallback()) {

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
        }

        inner class ViewHolder(val binding: ViewItemSearchBrandKeywordLetterBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: NavBrandKeywordLetter) = with(binding) {
                tvLetter.text = data.navBrandKeywordTitle
                if (data.isSelected) {
                    cvLetter.strokeColor = Color.parseColor("#c9000b")
                } else {
                    cvLetter.strokeColor = Color.parseColor("#f4f5f7")
                }
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<NavBrandKeywordLetter>() {
            override fun areItemsTheSame(oldItem: NavBrandKeywordLetter, newItem: NavBrandKeywordLetter): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: NavBrandKeywordLetter, newItem: NavBrandKeywordLetter): Boolean =
                oldItem == newItem
        }
    }
}
