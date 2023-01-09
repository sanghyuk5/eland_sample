package com.pionnet.eland.ui.leftmenu.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemLeftMenuBrandBinding
import com.pionnet.eland.databinding.ViewLeftMenuBrandModuleBinding
import com.pionnet.eland.model.LeftMenuData
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class LeftMenuBrandViewHolder(
    private val binding: ViewLeftMenuBrandModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val brandList = it.toMutableList().checkItemsAre<LeftMenuData.Data.Brand>()
            brandList?.let { it ->
                initView(it)
            }
        }
    }

    private fun initView(data: MutableList<LeftMenuData.Data.Brand>) = with(binding) {
        rvBrand.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx))
            adapter = BrandAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class BrandAdapter
        : ListAdapter<LeftMenuData.Data.Brand, BrandAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemLeftMenuBrandBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemLeftMenuBrandBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: LeftMenuData.Data.Brand) = with(binding) {
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).centerCrop().into(ivBrand)
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<LeftMenuData.Data.Brand>() {
        override fun areItemsTheSame(oldItem: LeftMenuData.Data.Brand, newItem: LeftMenuData.Data.Brand): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LeftMenuData.Data.Brand, newItem: LeftMenuData.Data.Brand): Boolean =
            oldItem == newItem
    }
}