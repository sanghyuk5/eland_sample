package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewHomeCategoryIconModuleBinding
import com.pionnet.eland.databinding.ViewItemCategoryIconBinding
import com.pionnet.eland.model.HomeData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp

class HomeCategoryIconViewHolder(
    private val binding: ViewHomeCategoryIconModuleBinding
) : BaseViewHolder(binding.root) {

    private var isMoreClick = false
    private lateinit var homeCategoryIconAdapter: HomeCategoryIconAdapter
    private var categoryIconData: List<HomeData.Data.CategoryIcon> ?= null

    init {
        binding.closeLayout.setOnClickListener {
            binding.closeLayout.visibility = View.GONE
            homeCategoryIconAdapter.apply {
                isMoreClick = true
                notifyItemChanged(9)
                submitList(categoryIconData!!.take(10))
            }
        }
    }

    private val moreClickListener = View.OnClickListener {
        binding.closeLayout.visibility = View.VISIBLE
        homeCategoryIconAdapter.apply {
            isMoreClick = false
            notifyItemChanged(9) //submitList(categoryIconData.toMutableList()) 해도 10번째 데이터가 변하지 않음
            submitList(categoryIconData)
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeCategoryIconData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.HomeCategoryIconData) = with(binding) {
        isMoreClick = data.isMoreClick
        categoryIconData = data.homeCategoryIconData
        list.apply {
            if (adapter == null) {
                homeCategoryIconAdapter = HomeCategoryIconAdapter()
                adapter = homeCategoryIconAdapter.apply {
                    val homeCategoryIconData = if (isMoreClick) data.homeCategoryIconData.take(10) else data.homeCategoryIconData
                    submitList(homeCategoryIconData)
                }
            }
        }
    }

    private inner class HomeCategoryIconAdapter
        : ListAdapter<HomeData.Data.CategoryIcon, HomeCategoryIconAdapter.ViewHolder>(DiffCallback()) {

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
            Logger.d("holder isMoreClick: $isMoreClick, position: $position")
            if (isMoreClick) {
                if (position == 9) {
                    holder.binding.image.visibility = View.GONE
                    holder.binding.more.visibility = View.VISIBLE
                    holder.binding.name.text = "더보기"
                    holder.itemView.setOnClickListener(moreClickListener)
                } else {
                    holder.bind(currentList[position])
                }
            } else {
                holder.bind(currentList[position])
            }
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

            fun bind(data: HomeData.Data.CategoryIcon) = with(binding) {
                layout.visibility = View.VISIBLE
                more.visibility = View.VISIBLE
                name.text = data.title
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).centerCrop().into(image)
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<HomeData.Data.CategoryIcon>() {
        override fun areItemsTheSame(oldItem: HomeData.Data.CategoryIcon, newItem: HomeData.Data.CategoryIcon): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: HomeData.Data.CategoryIcon, newItem: HomeData.Data.CategoryIcon): Boolean =
            oldItem == newItem
    }
}