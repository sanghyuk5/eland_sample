package com.pionnet.eland.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.pionnet.eland.*
import com.pionnet.eland.databinding.ActivitySearchBinding
import com.pionnet.eland.databinding.ViewItemSearchResultBrandBinding
import com.pionnet.eland.data.DataManager
import com.pionnet.eland.data.SearchResultBrandData
import com.pionnet.eland.ui.search.searchBrand.SearchBrandFragment
import com.pionnet.eland.ui.search.searchPopular.SearchPopularFragment
import com.pionnet.eland.ui.search.searchRecently.SearchRecentlyFragment
import com.pionnet.eland.utils.GlideApp
import com.pionnet.eland.utils.toPx
import com.pionnet.eland.views.GridMarginItemDecoration
import com.pionnet.eland.views.HorizontalMarginDecoration
import java.util.*

class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    private var tabInfo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            Logger.d("isSavedInstanceState is true, return")
            return
        }

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabInfo = intent.getStringExtra(DataManager.EXTRA_LINK) ?: "인기"

        initObserve()
        initView()
    }

    private fun initObserve() = with(viewModel) {
        brandResult.observe(this@SearchActivity) {
            binding.viewSearchResult.root.visibility = View.VISIBLE
            (binding.viewSearchResult.rvSearchResultBrand.adapter as? SearchResultBrandAdapter)?.submitList(it)
        }

        popularResult.observe(this@SearchActivity) {
            (binding.viewSearchResult.rvSearchResultRelated.adapter as? SearchPopularAdapter)?.submitList(it)
        }

        planShopResult.observe(this@SearchActivity) {
            (binding.viewSearchResult.rvSearchResultPlan.adapter as? SearchPlanShopAdapter)?.submitList(it)
        }
    }

    private fun initView() = with(binding) {
        viewSearchResult.root.visibility = View.GONE

        camera.setOnClickListener { navToSearchCamera() }

        searchText.addTextChangedListener(
            {_, _, _, _ -> },
            {_, _, _, _ -> },
            { edit ->
                close.visibility = View.VISIBLE

                val timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            viewModel.requestData()
                        }
                    }
                }, 1000)
            }
        )

        viewSearchResult.rvSearchResultBrand.apply {
            if (itemDecorationCount == 0) addItemDecoration(HorizontalMarginDecoration(3.toPx, 5.toPx, 5.toPx))
            adapter = SearchResultBrandAdapter()
        }
        viewSearchResult.rvSearchResultRelated.adapter = SearchPopularAdapter()
        viewSearchResult.rvSearchResultPlan.apply {
            if (itemDecorationCount == 0) addItemDecoration(GridMarginItemDecoration(3, 10.toPx))
            adapter = SearchPlanShopAdapter()
        }

        close.setOnClickListener { viewSearchResult.root.visibility = View.GONE }
        closeText.setOnClickListener { finish() }

        val tabTitleArray = arrayOf("인기", "최근", "브랜드")
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        if (tabInfo == "브랜드") {
            viewPager.currentItem = 2
        }
    }

    private class SearchResultBrandAdapter
        : ListAdapter<SearchResultBrandData.Result, SearchResultBrandAdapter.ViewHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemSearchResultBrandBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(currentList[position])
        }

        inner class ViewHolder(val binding: ViewItemSearchResultBrandBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: SearchResultBrandData.Result) = with(binding) {
                GlideApp.with(itemView.context).load("https://www.elandrs.com/upload" + data.imgPath).into(brandImg)
                brandName.text = data.keyword
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<SearchResultBrandData.Result>() {
            override fun areItemsTheSame(oldItem: SearchResultBrandData.Result, newItem: SearchResultBrandData.Result): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: SearchResultBrandData.Result, newItem: SearchResultBrandData.Result): Boolean =
                oldItem == newItem
        }
    }

    private class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return SearchPopularFragment()
                1 -> return SearchRecentlyFragment()
                2 -> return SearchBrandFragment()
            }
            return SearchPopularFragment()
        }
    }
}