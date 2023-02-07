package com.pionnet.eland.ui.goodsdetail.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemGoodsDetailReviewImageTextBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.model.GoodsDetailData.Data.GoodsReviewInfo.ReviewInfo.ReviewImageTextInfo.Review
import com.pionnet.eland.ui.viewholder.BaseViewHolder
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.checkItemsAre

class GoodsDetailReviewListViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? List<*>)?.let {
            val dataList = it.toMutableList().checkItemsAre<Review>()
            dataList?.let { data ->
                initView(data)
            }
        }
    }

    private fun initView(data: MutableList<Review>) = with(binding) {
        list.apply {
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            adapter = ReviewImageTextAdapter().apply {
                submitList(data)
            }
        }
    }

    private inner class ReviewImageTextAdapter
        : ListAdapter<Review, ReviewImageTextAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailReviewImageTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailReviewImageTextBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: Review) = with(binding) {
                if (data.imageUrl != null) {
                    goodsImg.visibility = View.VISIBLE
                    GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(goodsImg)
                } else {
                    goodsImg.visibility = View.GONE
                }

                id.text = data.userID
                profile.text = data.height + "   " + data.weight
                purchaseGoodsInfo.text = data.purchaseGoodsInfo
                comment.text = data.reviewComment
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
            oldItem == newItem
    }
}