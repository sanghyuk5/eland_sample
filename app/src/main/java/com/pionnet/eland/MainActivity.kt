package com.pionnet.eland

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.pionnet.eland.databinding.ActivityMainBinding
import com.pionnet.eland.databinding.ViewItemMainTabmenuBinding
import com.pionnet.eland.model.TabData
import com.pionnet.eland.ui.main.MainTabPagerAdapter
import com.pionnet.eland.ui.main.MainViewModel
import com.pionnet.eland.ui.main.splash.SplashFragment
import com.pionnet.eland.utils.dialogAlert

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val splashFragment by lazy { SplashFragment() }

    private lateinit var tabPagerAdapter: MainTabPagerAdapter

    private var mainTabs = listOf<TabData.TabInfo.HeaderIcon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            Logger.d("isSavedInstanceState is true, return")
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchMain()
    }

    private fun launchMain() {
        initSplash()
        initTopTab()
        initBottomMenu()
        initObserve()
        //reload(true)
    }

    private fun initSplash() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, splashFragment, "")
            .commitNow()
    }

    private fun initTopTab() = with(binding) {
        topBar.ivMenu.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LEFT_MENU))
        }

        topBar.rlSearch.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH))
        }

        tabPagerAdapter = MainTabPagerAdapter(this@MainActivity)
        viewPager.adapter = tabPagerAdapter

        viewPager.setCurrentItem(0, false)
    }

    private fun initBottomMenu() = with(binding) {
        bottomMenu.llSearchMenu.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LEFT_MENU))
        }

        bottomMenu.llBrand.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH, "브랜드"))
        }

        bottomMenu.ivHome.setOnClickListener {
            viewPager.currentItem = 0
        }

        bottomMenu.llDelivery.setOnClickListener {}

        bottomMenu.llGoods.setOnClickListener {}
    }

    private fun initObserve() {
        observeSplash()
        observeRefresh()
        observeLinkEvent()
        observeShowToast()
    }

    private fun observeSplash() = with(viewModel) {
        isReadyToMain.observe(this@MainActivity) {
            if (it == true) {
                supportFragmentManager.beginTransaction()
                    .remove(splashFragment)
                    .commitNow()
            }
        }
        isSplashError.observe(this@MainActivity) {
            if (!it) {
                dialogAlert(
                    this@MainActivity,
                    "오류가 발생하였습니다.",
                    okListener = { finish() }
                )
            }
        }

        tabResult.observe(this@MainActivity) {
            it.tabInfo.header_icon_list?.let { iconList ->
                TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
                    val customBinding = ViewItemMainTabmenuBinding.inflate(LayoutInflater.from(binding.tabs.context)).apply {
                        tvItemMainTabTitle.text = iconList[pos].menu_nm
                    }

                    tab.customView = customBinding.root
                }.attach()

                tabPagerAdapter.setData(iconList)
            }
        }
    }

    private fun reload(isReloadMain: Boolean = false) = with(viewModel) {
        if (isReloadMain) {
            //requestMainGnb()
        } else {
            requestRefreshCurrentTab()
        }
    }

    private fun requestRefreshCurrentTab() {
        val menuCD = mainTabs[binding.viewPager.currentItem].menu_cd
        val fragment = (binding.viewPager.adapter as? MainTabPagerAdapter)?.fragments?.get(menuCD)
        fragment?.let {
            it.onRequestRefresh()
        }
    }

    private fun observeRefresh() {
        viewModel.requestRefresh.observe(this) {
            it.getIfNotHandled()?.let { requestRefresh ->
                reload(requestRefresh.isWhole)
            }
        }
    }

    private fun observeLinkEvent() {
        EventBus.linkEvent.observe(this) { event ->
            event.getIfNotHandled()?.let {
                onLinkEvent(it)
            }
        }
    }

    override fun onLinkEvent(linkEvent: LinkEvent) {
        super.onLinkEvent(linkEvent)
    }

    private fun observeShowToast() {
        viewModel.showToast.observe(this) {
            Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_LONG).show()
        }
    }
}