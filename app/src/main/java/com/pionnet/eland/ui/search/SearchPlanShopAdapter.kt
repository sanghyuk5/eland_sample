package com.pionnet.eland.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewItemSearchPlanShopBinding
import com.pionnet.eland.model.SearchPlanShopData.Result.Planshop
import com.pionnet.eland.utils.GlideApp

class SearchPlanShopAdapter
    : ListAdapter<Planshop, SearchPlanShopAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewItemSearchPlanShopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(val binding: ViewItemSearchPlanShopBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Planshop) = with(binding) {
            GlideApp.with(itemView.context).load("https://www.elandrs.com/upload" + data.bannerImgPath).into(image)
            name.text = data.dispCtgNm
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Planshop>() {
        override fun areItemsTheSame(oldItem: Planshop, newItem: Planshop): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Planshop, newItem: Planshop): Boolean =
            oldItem == newItem
    }
}