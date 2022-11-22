package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.Target
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewHomeMultiBannerModuleBinding
import com.pionnet.eland.databinding.ViewItemBannerBinding
import com.pionnet.eland.model.Banner
import com.pionnet.eland.model.Goods
import com.pionnet.eland.ui.main.BannerDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.AdjustHeightImageViewTarget
import com.pionnet.eland.utils.GlideApp

class HomeMultiBannerViewHolder(
    private val binding: ViewHomeMultiBannerModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.HomeMultiBannerData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.HomeMultiBannerData) = with(binding) {
        rvBanner.apply {
            adapter = HomeBannerAdapter().apply {
                layoutManager = GridLayoutManager(binding.root.context, data.homeBannerData.size)
                submitList(data.homeBannerData)
            }
        }
    }

    private inner class HomeBannerAdapter
        : ListAdapter<Banner, HomeBannerAdapter.ViewHolder>(BannerDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemBannerBinding)
            : RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: Banner) = with(binding) {
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context)
                    .load("https:" + data.imageUrl)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(AdjustHeightImageViewTarget(ivBanner))
            }
        }
    }
}