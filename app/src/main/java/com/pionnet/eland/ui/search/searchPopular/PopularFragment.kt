package com.pionnet.eland.ui.search.searchPopular

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentSearchPopularBinding
import com.pionnet.eland.databinding.ViewItemSearchPlanShopBinding
import com.pionnet.eland.databinding.ViewItemSearchPopularBinding
import com.pionnet.eland.model.SearchPlanShop.Result.Planshop
import com.pionnet.eland.model.SearchRank
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.GridMarginItemDecoration

class PopularFragment : Fragment(R.layout.fragment_search_popular) {

    private val binding by viewBinding(FragmentSearchPopularBinding::bind)
    private val viewModel: PopularViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initView()
    }

    private fun observeData() = with(viewModel) {
        requestData()

        popularResult.observe(viewLifecycleOwner) {
            (binding.rvPopular.adapter as? SearchPopularAdapter)?.submitList(it)
        }

        planShopResult.observe(viewLifecycleOwner) {
            (binding.rvPlanShop.adapter as? SearchPlanShopAdapter)?.submitList(it?.result?.get(0)?.planshop)
        }
    }

    private fun initView() = with(binding) {
        rvPopular.adapter = SearchPopularAdapter()
        rvPlanShop.apply {
            if (itemDecorationCount == 0) addItemDecoration(GridMarginItemDecoration(3, 10.toPx))
            adapter = SearchPlanShopAdapter()
        }
    }

    private class SearchPopularAdapter
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
                tvRank.text = (position + 1).toString()
                if (data.isTopFive) tvRank.setTextColor(Color.parseColor("#c9000b"))
                else tvRank.setTextColor(Color.parseColor("#393939"))

                tvName.text = data.keyword

                data.rank?.let {
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

    private class SearchPlanShopAdapter
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
                GlideApp.with(itemView.context).load("https://www.elandrs.com/upload" + data.bannerImgPath).into(ivPlanShop)
                tvPlanShop.text = data.dispCtgNm
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<Planshop>() {
            override fun areItemsTheSame(oldItem: Planshop, newItem: Planshop): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Planshop, newItem: Planshop): Boolean =
                oldItem == newItem
        }
    }
}