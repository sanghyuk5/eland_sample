package com.pionnet.eland.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.request.target.Target
import com.pionnet.eland.databinding.ViewCommonMainBannerModuleBinding
import com.pionnet.eland.databinding.ViewItemBannerBinding
import com.pionnet.eland.data.Banner
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.utils.AdjustHeightImageViewTarget
import com.pionnet.eland.utils.GlideApp
import kotlinx.coroutines.*

class CommonMainBannerViewHolder(
    private val binding: ViewCommonMainBannerModuleBinding,
    private val lifecycleOwner: LifecycleOwner
) : BaseViewHolder(binding.root) {

    private var bannerPosition = 0

    private lateinit var job: Job

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.CommonMainBanner)?.let {
            initView(it.mainBannerData)
        }
    }

    private fun initView(data: List<Banner>) = with(binding) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerPosition = position

                now.text = ((bannerPosition % data.size) + 1).toString()
            }
        })

        bannerPosition = Int.MAX_VALUE / 2
        viewPager.adapter = ViewPagerAdapter(data)
        bannerPosition -= (bannerPosition % data.size)
        binding.viewPager.setCurrentItem(bannerPosition, false)

        now.text = ((bannerPosition % data.size) + 1).toString()
        total.text = "/" + data.size.toString() + "+"
    }

    private inner class ViewPagerAdapter(private val data: List<Banner>) : RecyclerView.Adapter<ViewHolder>() {

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
            holder.bind(data[position % data.size])
        }

        override fun getItemCount(): Int = Int.MAX_VALUE
    }

    private inner class ViewHolder(val binding: ViewItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Banner) = with(binding) {
            GlideApp.with(itemView.context)
                .load("https:" + data.imageUrl)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(AdjustHeightImageViewTarget(image))
        }
    }

    private fun scrollJobCreate() {
        job = lifecycleOwner.lifecycleScope.launchWhenResumed {
            while (lifecycleOwner.lifecycleScope.isActive) {
                delay(3000)
                binding.viewPager.setCurrentItem(++bannerPosition, true)
            }
        }
    }

    override fun onAppeared() {
        super.onAppeared()
        scrollJobCreate()
    }

    override fun onDisappeared() {
        super.onDisappeared()

        job.cancel()
    }
}