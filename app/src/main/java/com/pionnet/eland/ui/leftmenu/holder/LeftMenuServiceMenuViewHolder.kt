package com.pionnet.eland.ui.leftmenu.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemLeftMenuServiceBinding
import com.pionnet.eland.databinding.ViewLeftMenuServiceModuleBinding
import com.pionnet.eland.model.LeftMenuData
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre

class LeftMenuServiceMenuViewHolder(
    private val binding: ViewLeftMenuServiceModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val serviceMenuList = it.toMutableList().checkItemsAre<LeftMenuData.Data.ServiceMenu>()
            serviceMenuList?.let { it ->
                initView(it)
            }
        }
    }

    private fun initView(data: MutableList<LeftMenuData.Data.ServiceMenu>) = with(binding) {
        rvServiceMenu.adapter = ServiceMenuAdapter().apply {
            submitList(data)
        }
    }

    private inner class ServiceMenuAdapter
        : ListAdapter<LeftMenuData.Data.ServiceMenu, ServiceMenuAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemLeftMenuServiceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemLeftMenuServiceBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: LeftMenuData.Data.ServiceMenu) = with(binding) {
                tvTitle.text = data.serviceMenu
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<LeftMenuData.Data.ServiceMenu>() {
        override fun areItemsTheSame(oldItem: LeftMenuData.Data.ServiceMenu, newItem: LeftMenuData.Data.ServiceMenu): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: LeftMenuData.Data.ServiceMenu, newItem: LeftMenuData.Data.ServiceMenu): Boolean =
            oldItem == newItem
    }
}