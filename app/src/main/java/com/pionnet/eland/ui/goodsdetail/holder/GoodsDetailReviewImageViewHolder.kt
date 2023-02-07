package com.pionnet.eland.ui.goodsdetail.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemGoodsDetailReviewImageBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.GoodsDetailData.Data.GoodsReviewInfo.ReviewInfo.ReviewImageTextInfo
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.GridMarginItemDecoration

class GoodsDetailReviewImageViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ReviewImageTextInfo)?.let {
            initView(it)
        }
    }

    private fun initView(data: ReviewImageTextInfo) = with(binding) {
        var dataSize = data.reviewCount ?: 0
        if (dataSize > 5) dataSize = 5

        list.apply {
            if (itemDecorationCount == 0) addItemDecoration(GridMarginItemDecoration(dataSize, 10.toPx))
            layoutManager = GridLayoutManager(root.context, dataSize)
            adapter = ReviewImageAdapter().apply {
                submitList(data.reviewList)
                if (data.reviewCount?.compareTo(5) == 1) isMoreExist = true
            }
        }
    }

    private inner class ReviewImageAdapter
        : ListAdapter<ReviewImageTextInfo.Review, ReviewImageAdapter.ViewHolder>(DiffCallback()) {

        var isMoreExist = false

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailReviewImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position], position)
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailReviewImageBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: ReviewImageTextInfo.Review, position: Int) = with(binding) {
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(goodsImg)

                if (isMoreExist && position == currentList.size - 1) moreImg.visibility = View.VISIBLE
                else moreImg.visibility = View.GONE
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<ReviewImageTextInfo.Review>() {
        override fun areItemsTheSame(oldItem: ReviewImageTextInfo.Review, newItem: ReviewImageTextInfo.Review): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ReviewImageTextInfo.Review, newItem: ReviewImageTextInfo.Review): Boolean =
            oldItem == newItem
    }
}