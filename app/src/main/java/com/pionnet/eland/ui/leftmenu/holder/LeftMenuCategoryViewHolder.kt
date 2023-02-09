package com.pionnet.eland.ui.leftmenu.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewItemCategoryIconBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.LeftMenuData
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.checkItemsAre

class LeftMenuCategoryViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val dataList = it.toMutableList().checkItemsAre<LeftMenuData.Data.Category.CategoryMenu>()
            dataList?.let { data ->
                initView(data)
            }
        }
    }

    private fun initView(data: MutableList<LeftMenuData.Data.Category.CategoryMenu>) = with(binding) {
        list.apply {
            layoutManager = GridLayoutManager(root.context, 5)
            adapter = CategoryAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class CategoryAdapter
        : ListAdapter<LeftMenuData.Data.Category.CategoryMenu, CategoryAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemCategoryIconBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemCategoryIconBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: LeftMenuData.Data.Category.CategoryMenu) = with(binding) {
                root.setBackgroundResource(R.drawable.bg_left_menu_category)
                name.text = data.menuTitle
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).centerCrop().into(image)
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<LeftMenuData.Data.Category.CategoryMenu>() {
        override fun areItemsTheSame(oldItem: LeftMenuData.Data.Category.CategoryMenu, newItem: LeftMenuData.Data.Category.CategoryMenu): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LeftMenuData.Data.Category.CategoryMenu, newItem: LeftMenuData.Data.Category.CategoryMenu): Boolean =
            oldItem == newItem
    }
}