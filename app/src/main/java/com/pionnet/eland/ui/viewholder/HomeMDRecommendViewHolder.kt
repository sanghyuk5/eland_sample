package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewHomeMdRecommendModuleBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewItemMdRecommendCategoryBinding
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.ui.main.HorizontalAdapter
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeMDRecommendViewHolder(
    private val binding: ViewHomeMdRecommendModuleBinding
) : BaseViewHolder(binding.root) {

    private var mdRecommend: HomeData.Data.MDRecommend? = null

    private val tabClickCallback: ItemClickIntCallback = { index ->
        mdRecommend?.categoryList?.select(index) {
            mdRecommend?.categoryList = it.toMutableList()
            (binding.rvGoods.adapter as? HorizontalAdapter)?.submitList(it.getOrNull(index)?.goodsList)
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeMDRecommendData)?.let {
            initView(it.homeMDRecommendData)
        }
    }

    private fun initView(data: HomeData.Data.MDRecommend) = with(binding) {
        mdRecommend = data
        viewTitle.tvTitle.text = data.title

        var selectedTabItem = data.categoryList?.indexOfFirst { it.isSelected } ?: -1
        if (selectedTabItem == -1) selectedTabItem = 0

        rvCategory.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 7.toPx, 7.toPx))

            adapter = HomeMDCategoryAdapter(tabClickCallback).apply {
                submitList(data.categoryList)
            }

            smoothScrollToPosition(selectedTabItem)
        }

        rvGoods.apply {
            if (adapter == null) {
                adapter = HorizontalAdapter().apply {
                    submitList(data.categoryList!![selectedTabItem].goodsList)
                }
            }
        }
    }

    private fun List<HomeData.Data.MDRecommend.CategoryList>.select(index: Int, callback: (List<HomeData.Data.MDRecommend.CategoryList>) -> Unit) {
        val data = this.map { it.copy() }.toMutableList()
        val selectedItem = data.indexOfFirst { it.isSelected }
        if (selectedItem != -1 && selectedItem != index) {
            data.getOrNull(selectedItem)?.isSelected = false
            data.getOrNull(index)?.isSelected = true
            binding.rvCategory.apply {
                (adapter as? HomeMDCategoryAdapter)?.submitList(data)
                CoroutineScope(Dispatchers.Main).launch {
                    delay(200)
                    scrollToPosition(index)
                }
            }
            callback.invoke(data)
        }
    }

    private inner class HomeMDCategoryAdapter(private val tabClickCallback: ItemClickIntCallback)
        : ListAdapter<HomeData.Data.MDRecommend.CategoryList, HomeMDCategoryAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemMdRecommendCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
            holder.itemView.setOnClickListener {
                tabClickCallback.invoke(position)
            }
        }

        private inner class ViewHolder(val binding: ViewItemMdRecommendCategoryBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: HomeData.Data.MDRecommend.CategoryList) = with(binding) {
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(ivCategory)
                tvCategory.text = data.title

                ivBar.visibility = if (data.isSelected) View.VISIBLE else View.GONE
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<HomeData.Data.MDRecommend.CategoryList>() {
        override fun areItemsTheSame(oldItem: HomeData.Data.MDRecommend.CategoryList, newItem: HomeData.Data.MDRecommend.CategoryList): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HomeData.Data.MDRecommend.CategoryList, newItem: HomeData.Data.MDRecommend.CategoryList): Boolean =
            oldItem == newItem
    }
}