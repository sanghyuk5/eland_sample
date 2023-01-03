package com.pionnet.eland.ui.viewholder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.databinding.ViewEkidsRecommendCategoryModuleBinding
import com.pionnet.eland.databinding.ViewItemEkidsRecommendCategoryBinding
import com.pionnet.eland.model.EKidsData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EKidsRecommendCategoryViewHolder(
    private val binding: ViewEkidsRecommendCategoryModuleBinding
) : BaseViewHolder(binding.root) {

    private var groupData: List<EKidsData.Data.ExpandGroup.Group>? = null
    private var isWeeklyBest = false

    private val tabClickCallback: ItemClickIntCallback = { index ->
        groupData?.select(index)

        if (isWeeklyBest) {
            EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_E_KIDS_WEEKLY, index))
        } else {
            EventBus.fire(HolderEvent(HolderEventType.TAB_CLICK_E_KIDS_ARRIVAL, index))
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.EKidsRecommendCategoryData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.EKidsRecommendCategoryData) = with(binding) {
        groupData = data.categoryData
        isWeeklyBest = data.viewType == "weeklyBest"

        rvCategory.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 7.toPx, 7.toPx))

            adapter = CategoryAdapter(tabClickCallback).apply {
                submitList(data.categoryData)
            }
        }
    }

    private fun List<EKidsData.Data.ExpandGroup.Group>.select(index: Int) {
        val data = this.map { it.copy() }.toMutableList()
        val selectedItem = data.indexOfFirst { it.isSelected }
        if (selectedItem != -1 && selectedItem != index) {
            data.getOrNull(selectedItem)?.isSelected = false
            data.getOrNull(index)?.isSelected = true
            binding.rvCategory.apply {
                (adapter as? CategoryAdapter)?.submitList(data)
                CoroutineScope(Dispatchers.Main).launch {
                    delay(200)
                    scrollToPosition(index)
                }
            }
        }
    }

    private inner class CategoryAdapter(private val tabClickCallback: ItemClickIntCallback)
        : ListAdapter<EKidsData.Data.ExpandGroup.Group, CategoryAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemEkidsRecommendCategoryBinding.inflate(
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

        private inner class ViewHolder(val binding: ViewItemEkidsRecommendCategoryBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: EKidsData.Data.ExpandGroup.Group) = with(binding) {
                if (data.isSelected) {
                    tvCategory.setTextColor(Color.parseColor("#ffffff"))
                    cvCategory.setCardBackgroundColor(Color.parseColor("#414141"))
                } else {
                    tvCategory.setTextColor(Color.parseColor("#414141"))
                    cvCategory.setCardBackgroundColor(Color.parseColor("#ffffff"))
                }

                tvCategory.text = data.name
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<EKidsData.Data.ExpandGroup.Group>() {
        override fun areItemsTheSame(oldItem: EKidsData.Data.ExpandGroup.Group, newItem: EKidsData.Data.ExpandGroup.Group): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: EKidsData.Data.ExpandGroup.Group, newItem: EKidsData.Data.ExpandGroup.Group): Boolean =
            oldItem == newItem
    }
}