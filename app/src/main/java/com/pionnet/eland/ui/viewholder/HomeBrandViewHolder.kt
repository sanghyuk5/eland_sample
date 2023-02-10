package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemBrandBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class HomeBrandViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeBrandData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.HomeBrandData) = with(binding) {
        list.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 7.toPx, 7.toPx))
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeBrandAdapter().apply {
                submitList(data.homeBrandData)
            }
        }
    }

    private inner class HomeBrandAdapter
        : ListAdapter<HomeData.Data.Brand, HomeBrandAdapter.ViewHolder>(DiffCallback()) {

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

            fun bind(data: HomeData.Data.Brand) = with(binding) {
                name.text = data.brand
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<HomeData.Data.Brand>() {
        override fun areItemsTheSame(oldItem: HomeData.Data.Brand, newItem: HomeData.Data.Brand): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: HomeData.Data.Brand, newItem: HomeData.Data.Brand): Boolean =
            oldItem == newItem
    }
}