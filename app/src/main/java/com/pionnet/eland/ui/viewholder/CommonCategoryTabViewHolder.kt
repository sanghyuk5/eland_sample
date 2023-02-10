package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.databinding.ViewItemCommonCategoryBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.Category
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CommonCategoryTabViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    private var category: ModuleData.CommonCategoryTab? = null
    private var viewType = ""

    private val tabClickCallback: ItemClickIntCallback = { index ->
        category?.categoryData?.select(index) {
            category?.categoryData = it.toMutableList()
            if ("home".equals(viewType, true)) {
                EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_HOME, index))
            } else if ("lucky".equals(viewType, true)) {
                EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_LUCKY, index))
            } else if ("best".equals(viewType, true)) {
                EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_BEST, index))
            } else if ("storeShop".equals(viewType, true)) {
                EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_STORE_SHOP, index))
            } else if ("plan".equals(viewType, true)) {
                EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_PLAN, index))
            }
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonCategoryTab)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonCategoryTab) = with(binding) {
        category = data
        viewType = data.viewType

        var selectedTabItem = data.categoryData.indexOfFirst { it.isSelected }
        if (selectedTabItem == -1) selectedTabItem = 0

        list.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 7.toPx, 7.toPx))
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(tabClickCallback).apply {
                submitList(data.categoryData)
            }

            smoothScrollToPosition(selectedTabItem)
        }
    }

    private fun List<Category>.select(index: Int, callback: (List<Category>) -> Unit) {
        val data = this.map { it.copy() }.toMutableList()
        val selectedItem = data.indexOfFirst { it.isSelected }
        if (selectedItem != -1 && selectedItem != index) {
            data.getOrNull(selectedItem)?.isSelected = false
            data.getOrNull(index)?.isSelected = true
            binding.list.apply {
                (adapter as? CategoryAdapter)?.submitList(data)
                CoroutineScope(Dispatchers.Main).launch {
                    delay(200)
                    scrollToPosition(index)
                }
            }
            callback.invoke(data)
        }
    }

    private inner class CategoryAdapter(private val tabClickCallback: ItemClickIntCallback)
        : ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemCommonCategoryBinding.inflate(
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

        private inner class ViewHolder(val binding: ViewItemCommonCategoryBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: Category) = with(binding) {
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)
                name.text = data.title

                bar.visibility = if (data.isSelected) View.VISIBLE else View.GONE
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }
}