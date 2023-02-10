package com.pionnet.eland.ui.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.databinding.ViewItemEShopCategoryItemBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.EShopData
import com.pionnet.eland.ui.main.ModuleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EShopCategoryViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    private var category: List<EShopData.Data.Group>? = null
    private var isIssue = false

    private val tabClickCallback: ItemClickIntCallback = { index ->
        category?.select(index) {
            category = it.toMutableList()
            if (isIssue) {
                EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_E_SHOP_ISSUE, index))
            } else {
                EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_E_SHOP_ARRIVAL, index))
            }
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.EShopCategoryData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.EShopCategoryData) = with(binding) {
        category = data.categoryData
        isIssue = data.viewType == "issue"

        list.apply {
            adapter = CategoryAdapter(tabClickCallback).apply {
                layoutManager = GridLayoutManager(binding.root.context, data.categoryData.size)
                submitList(data.categoryData)
            }
        }
    }

    private fun List<EShopData.Data.Group>.select(index: Int, callback: (List<EShopData.Data.Group>) -> Unit) {
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
        : ListAdapter<EShopData.Data.Group, CategoryAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemEShopCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position], position)
            holder.itemView.setOnClickListener {
                tabClickCallback.invoke(position)
            }
        }

        private inner class ViewHolder(val binding: ViewItemEShopCategoryItemBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: EShopData.Data.Group, position: Int) = with(binding) {
                if (data.isSelected) {
                    name.setTextColor(Color.parseColor("#c9000b"))
                    bar.visibility = View.VISIBLE
                } else {
                    name.setTextColor(Color.parseColor("#353535"))
                    bar.visibility = View.GONE
                }

                divider.visibility = if (position == currentList.size - 1) View.GONE else View.VISIBLE

                name.text = data.title
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<EShopData.Data.Group>() {
        override fun areItemsTheSame(oldItem: EShopData.Data.Group, newItem: EShopData.Data.Group): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: EShopData.Data.Group, newItem: EShopData.Data.Group): Boolean =
            oldItem == newItem
    }
}