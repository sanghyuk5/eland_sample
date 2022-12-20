package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewEkidsCategoryModuleBinding
import com.pionnet.eland.databinding.ViewItemBrandBinding
import com.pionnet.eland.model.EKidsData
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class EKidsCategoryViewHolder(
    private val binding: ViewEkidsCategoryModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.EKidsCategoryData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.EKidsCategoryData) = with(binding) {
        rvCategory.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 7.toPx, 7.toPx))
            adapter = CategoryAdapter().apply {
                submitList(data.categoryData)
            }
        }
    }

    private inner class CategoryAdapter
        : ListAdapter<EKidsData.Data.Category, CategoryAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemBrandBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemBrandBinding)
            : RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: EKidsData.Data.Category) = with(binding) {
                tvName.text = data.name
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imagePath).centerCrop().into(ivIcon)
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<EKidsData.Data.Category>() {
        override fun areItemsTheSame(oldItem: EKidsData.Data.Category, newItem: EKidsData.Data.Category): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: EKidsData.Data.Category, newItem: EKidsData.Data.Category): Boolean =
            oldItem == newItem
    }
}