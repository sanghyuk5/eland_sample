package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ViewItemEkidsBrandBinding
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.data.Banner
import com.pionnet.eland.ui.main.BannerDiffCallback
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.HorizontalMarginDecoration

class EKidsBrandViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.EKidsBrandData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.EKidsBrandData) = with(binding) {
        list.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(5.toPx, 7.toPx, 7.toPx))
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BannerAdapter().apply {
                submitList(data.bannerData)
            }
        }
    }

    private inner class BannerAdapter
        : ListAdapter<Banner, BannerAdapter.ViewHolder>(BannerDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemEkidsBrandBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemEkidsBrandBinding)
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
                name.text = data.name
                linkUrl = data.linkUrl
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(image)
            }
        }
    }
}