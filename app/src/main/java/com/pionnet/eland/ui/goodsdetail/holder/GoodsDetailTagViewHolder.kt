package com.pionnet.eland.ui.goodsdetail.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemGoodsDetailTagBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.data.GoodsDetailData
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.checkItemsAre

class GoodsDetailTagViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val dataList = it.toMutableList().checkItemsAre<GoodsDetailData.Data.Tag>()
            dataList?.let { data ->
                initView(data)
            }
        }
    }

    private fun initView(data: MutableList<GoodsDetailData.Data.Tag>) = with(binding) {
        list.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            marginStart = 10
            marginEnd = 10
        }

        list.apply {
            layoutManager = object: FlexboxLayoutManager(itemView.context) {
                override fun canScrollVertically(): Boolean = false
            }.apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }

            adapter = TagAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class TagAdapter
        : ListAdapter<GoodsDetailData.Data.Tag, TagAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailTagBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailTagBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: GoodsDetailData.Data.Tag) = with(binding) {
                linkUrl = data.link
                name.text = data.name
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<GoodsDetailData.Data.Tag>() {
        override fun areItemsTheSame(oldItem: GoodsDetailData.Data.Tag, newItem: GoodsDetailData.Data.Tag): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: GoodsDetailData.Data.Tag, newItem: GoodsDetailData.Data.Tag): Boolean =
            oldItem == newItem
    }
}