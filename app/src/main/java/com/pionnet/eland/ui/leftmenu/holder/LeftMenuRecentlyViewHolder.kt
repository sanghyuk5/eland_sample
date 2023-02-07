package com.pionnet.eland.ui.leftmenu.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemLeftMenuRecentlyBinding
import com.pionnet.eland.databinding.ViewLeftMenuRecentlyModuleBinding
import com.pionnet.eland.model.LeftMenuData
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.checkItemsAre

class LeftMenuRecentlyViewHolder(
    private val binding: ViewLeftMenuRecentlyModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val dataList = it.toMutableList().checkItemsAre<LeftMenuData.Data.Recently>()
            dataList?.let { data ->
                initView(data)
            }
        }
    }

    private fun initView(data: MutableList<LeftMenuData.Data.Recently>) = with(binding) {
        if (data.isNullOrEmpty()) tvItemEmpty.visibility = View.VISIBLE
        else {
            tvItemEmpty.visibility = View.GONE
            rvRecently.adapter = RecentlyAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class RecentlyAdapter
        : ListAdapter<LeftMenuData.Data.Recently, RecentlyAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemLeftMenuRecentlyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemLeftMenuRecentlyBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: LeftMenuData.Data.Recently) = with(binding) {
                GlideApp.with(itemView.context).load("https:" + data.imgPath).into(ivRecent)
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<LeftMenuData.Data.Recently>() {
        override fun areItemsTheSame(oldItem: LeftMenuData.Data.Recently, newItem: LeftMenuData.Data.Recently): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LeftMenuData.Data.Recently, newItem: LeftMenuData.Data.Recently): Boolean =
            oldItem == newItem
    }
}