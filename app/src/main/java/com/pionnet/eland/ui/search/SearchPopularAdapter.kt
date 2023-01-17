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
            tvName.text = data.keyword

            data.rank?.let {
                tvRank.visibility = View.VISIBLE
                ivRank.visibility = View.VISIBLE

                tvRank.text = (position + 1).toString()
                if (data.isTopFive) tvRank.setTextColor(Color.parseColor("#c9000b"))
                else tvRank.setTextColor(Color.parseColor("#393939"))

                val rank = it.toIntOrNull()
                if (rank != null) {
                    if (rank > 0) {
                        ivRank.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24)
                    } else if (rank == 0) {
                        ivRank.setImageResource(R.drawable.ic_baseline_horizontal_rule_24)
                    } else {
                        ivRank.setImageResource(R.drawable.ic_baseline_arrow_downward_gray_24)
                    }
                } else {
                    if (it == "new") ivRank.setImageResource(R.drawable.ic_baseline_fiber_new_24)
                }
            } ?: run {
                tvRank.visibility = View.GONE
                ivRank.visibility = View.GONE
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