package com.pionnet.eland.ui.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewItemSearchPopularBinding
import com.pionnet.eland.model.SearchRank

class SearchPopularAdapter
    : ListAdapter<SearchRank, SearchPopularAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewItemSearchPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    inner class ViewHolder(val binding: ViewItemSearchPopularBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SearchRank, position: Int) = with(binding) {
            name.text = data.keyword

            data.rank?.let {
                rank.visibility = View.VISIBLE
                image.visibility = View.VISIBLE

                rank.text = (position + 1).toString()
                if (data.isTopFive) rank.setTextColor(Color.parseColor("#c9000b"))
                else rank.setTextColor(Color.parseColor("#393939"))

                val rank = it.toIntOrNull()
                if (rank != null) {
                    if (rank > 0) {
                        image.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                    } else if (rank == 0) {
                        image.setImageResource(R.drawable.ic_baseline_horizontal_rule_24)
                    } else {
                        image.setImageResource(R.drawable.ic_baseline_arrow_downward_gray_24)
                    }
                } else {
                    if (it == "new") image.setImageResource(R.drawable.ic_baseline_fiber_new_24)
                }
            } ?: run {
                rank.visibility = View.GONE
                image.visibility = View.GONE
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SearchRank>() {
        override fun areItemsTheSame(oldItem: SearchRank, newItem: SearchRank): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SearchRank, newItem: SearchRank): Boolean =
            oldItem == newItem
    }
}