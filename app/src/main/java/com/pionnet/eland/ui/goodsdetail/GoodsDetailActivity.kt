package com.pionnet.eland.ui.goodsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.request.target.Target
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.pionnet.eland.BaseActivity
import com.pionnet.eland.EventBus
import com.pionnet.eland.LinkEvent
import com.pionnet.eland.databinding.ActivityGoodsDetailBinding
import com.pionnet.eland.databinding.ViewItemBannerBinding
import com.pionnet.eland.databinding.ViewItemGoodsDetailPopularStyleBinding
import com.pionnet.eland.model.Banner
import com.pionnet.eland.model.Goods
import com.pionnet.eland.model.GoodsDetailData
import com.pionnet.eland.ui.goodsdetail.info.GoodsDetailInfoFragment
import com.pionnet.eland.ui.goodsdetail.order.GoodsDetailOrderFragment
import com.pionnet.eland.ui.goodsdetail.question.GoodsDetailQuestionFragment
import com.pionnet.eland.ui.goodsdetail.review.GoodsDetailReviewFragment
import com.pionnet.eland.ui.main.GoodsDiffCallback
import com.pionnet.eland.utils.AdjustHeightImageViewTarget
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.checkItemsAre
import com.pionnet.eland.utils.priceFormat

class GoodsDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityGoodsDetailBinding
    private lateinit var viewModel: GoodsDetailViewModel

    private lateinit var tabPagerAdapter: GoodsDetailTabPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            Logger.d("isSavedInstanceState is true, return")
            return
        }

        viewModel = ViewModelProvider(this).get(GoodsDetailViewModel::class.java)
        binding = ActivityGoodsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initView()
    }

    private fun initViewModel() = with(viewModel) {
        requestData()

        result.observe(this@GoodsDetailActivity) { data ->
            setView(data)

            val tabTitleArray = arrayOf("상품정보", "리뷰($reviewCount)", "Q&A($questionCount)", "주문정보")

            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
                tab.text = tabTitleArray[pos]
            }.attach()

            tabPagerAdapter.setData(tabTitleArray, data)
        }
    }

    private fun initView() = with(binding) {
        tabPagerAdapter = GoodsDetailTabPagerAdapter(this@GoodsDetailActivity)
        viewPager.apply {
            adapter = tabPagerAdapter
            offscreenPageLimit = 4
        }

        top.setOnClickListener {
            bar.setExpanded(true)
        }
    }

    class GoodsDetailTabPagerAdapter(fragmentActivity: FragmentActivity)
        : FragmentStateAdapter(fragmentActivity) {

        private var data: GoodsDetailData.Data ?= null
        private var titleData: Array<String> = emptyArray()

        override fun getItemCount(): Int = titleData.size

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    if (data != null) {
                        return GoodsDetailInfoFragment.create(data!!)
                    }
                    return GoodsDetailInfoFragment()
                }
                1 -> {
                    data?.goodsInfo?.goodsReviewInfo?.let {
                        return GoodsDetailReviewFragment.create(it)
                    }
                    return GoodsDetailReviewFragment()
                }
                2 -> {
                    data?.goodsInfo?.goodsQuestionInfo?.let {
                        return GoodsDetailQuestionFragment.create(it)
                    }
                    return GoodsDetailQuestionFragment()
                }
                3 -> {
                    data?.orderInfo?.let {
                        return GoodsDetailOrderFragment.create(it)
                    }
                    return GoodsDetailOrderFragment()
                }
            }
            return GoodsDetailInfoFragment()
        }

        fun setData(titleData: Array<String>, data: GoodsDetailData.Data) {
            this.titleData = titleData
            this.data = data
            notifyDataSetChanged()
        }
    }

    private fun setView(data: GoodsDetailData.Data) = with(binding) {
        data.topImageList?.let {
            val bannerList = it.toMutableList().checkItemsAre<Banner>()
            bannerList?.let { it ->
                setTopBanner(it)
            }
        }

        data.goodsInfo?.let {
            setGoodsInfo(it)
        }

        data.sellerPopularStyle?.let {
            setSellerPopularStyle(it)
        }
    }

    private fun setTopBanner(data: List<Banner>) = with(binding.goodsInfo) {
        var bannerPosition = Int.MAX_VALUE / 2
        viewPager.adapter = ViewPagerAdapter(data)
        bannerPosition -= (bannerPosition % data.size)
        viewPager.setCurrentItem(bannerPosition, false)

//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            Logger.d("hyuk gang")
//        }.attach()
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

    private fun setGoodsInfo(data: Goods) = with(binding.goodsInfo) {
        GlideApp.with(this@GoodsDetailActivity).load("https:" + data.brandImageUrl).into(brandImg)
        brandName.text = data.brandName
        goodsName.text = data.goodsName
        saleRate.visibility = if (data.saleRate != null && data.saleRate != 0) View.VISIBLE else View.GONE
        saleRate.text = data.saleRate.toString() + "%"
        priceBefore.visibility = if (data.marketPrice != null && data.marketPrice != 0) View.VISIBLE else View.INVISIBLE
        priceBefore.text = priceFormat(data.marketPrice ?: 0)
        priceAfter.text = priceFormat(data.salePrice ?: 0)
        priceAfter.text = priceFormat(data.finalPrice ?: 0) + "원"
        priceBefore2.text = priceFormat(data.marketPrice ?: 0) + "원"
        priceAfter2.text = "-" + priceFormat(data.salePrice ?: 0) + "원"
        couponSalePrice.text = "-" + priceFormat(data.couponSalePrice ?: 0) + "원"
        fastPayPoint.text = data.point
    }

    private fun setSellerPopularStyle(data: GoodsDetailData.Data.TitleGoods) = with(binding.popularStyle) {
        title.text = data.title

        list.apply {
            adapter = GoodsDetailPopularStyleAdapter().apply {
                submitList(data.goods)
            }
        }
    }

    private inner class GoodsDetailPopularStyleAdapter
        : ListAdapter<Goods, GoodsDetailPopularStyleAdapter.ViewHolder>(GoodsDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemGoodsDetailPopularStyleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        private inner class ViewHolder(val binding: ViewItemGoodsDetailPopularStyleBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private var linkUrl: String? = null

            init {
                itemView.setOnClickListener {
                    linkUrl?.let {
                        EventBus.fire(LinkEvent(it))
                    }
                }
            }

            fun bind(data: Goods) = with(binding) {
                GlideApp.with(itemView.context).load("https:" + data.imageUrl).into(goodsImg)
                goodsName.text = data.goodsName
            }
        }
    }
}

