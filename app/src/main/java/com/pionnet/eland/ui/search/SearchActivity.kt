package com.pionnet.eland.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.pionnet.eland.BaseActivity
import com.pionnet.eland.databinding.ActivitySearchBinding
import com.pionnet.eland.localData.DataManager
import com.pionnet.eland.ui.search.searchBrand.SearchBrandFragment
import com.pionnet.eland.ui.search.searchPopular.PopularFragment
import com.pionnet.eland.ui.search.searchRecently.SearchRecentlyFragment

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
        initViewModel()
        initView()
    }

    private fun initObserve() {

    }

    private fun initViewModel() {

    }

    private fun initView() = with(binding) {
        ivCamera.setOnClickListener {

        }

        tvClose.setOnClickListener {
            finish()
        }

        val tabTitleArray = arrayOf(
            "인기",
            "최근",
            "브랜드"
        )

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        if (tabInfo == "브랜드") {
            viewPager.currentItem = 2
        }
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return PopularFragment()
                1 -> return SearchRecentlyFragment()
                2 -> return SearchBrandFragment()
            }
            return PopularFragment()
        }
    }
}