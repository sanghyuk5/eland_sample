package com.pionnet.eland

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.pionnet.eland.databinding.ActivityMainBinding
import com.pionnet.eland.model.DataSource
import com.pionnet.eland.model.TabData
import com.pionnet.eland.ui.main.MainTabMenuAdapter
import com.pionnet.eland.ui.main.MainTabPagerAdapter
import com.pionnet.eland.ui.main.MainViewModel
import com.pionnet.eland.ui.main.splash.SplashFragment
import com.pionnet.eland.utils.dialogAlert
import com.pionnet.eland.utils.getDisplaySize

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val splashFragment by lazy { SplashFragment() }

    private lateinit var tabPagerAdapter: MainTabPagerAdapter
    private lateinit var tabAdapter: MainTabMenuAdapter

    private var tabCurrentPosition = 0
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
        initObserve()

        //reload(true)
    }

    private fun initSplash() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, splashFragment, "")
            .commitNow()
    }

    private fun initObserve() {
        observeSplash()
        observeRefresh()
        observeLinkEvent()
        observeShowToast()
    }

    private fun reload(
        isReloadMain: Boolean = false
    ) {
        with(viewModel) {
            if (isReloadMain) {
                //requestMainGnb()
            } else {
                requestRefreshCurrentTab()
            }
        }
    }

    private fun requestRefreshCurrentTab() {
        val menuCD = mainTabs[tabCurrentPosition].menu_cd
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

    private fun observeSplash() = with(viewModel) {
        tabResult.observe(this@MainActivity) {
            it.tabInfo.header_icon_list?.let { iconList ->
                tabPagerAdapter.setData(iconList)
                tabAdapter.updateData(iconList)
            }
        }

        homeResult.observe(this@MainActivity) {

        }

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

    private fun initTopTab() = with(binding) {
        topBar.ivMenu.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LEFT_MENU))
        }

        topBar.rlSearch.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH))
        }

        tabPagerAdapter = MainTabPagerAdapter(this@MainActivity)
        tabAdapter = MainTabMenuAdapter()

        viewPager.adapter = tabPagerAdapter
        val tabMenuLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvMainTabmenu.apply {
            adapter = tabAdapter
            layoutManager = tabMenuLayoutManager
        }

        viewPager.setCurrentItem(0, false)
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabCurrentPosition = position
                tabAdapter.updatePosition(position)
                tabMenuLayoutManager.scrollToPositionWithOffset(position, getDisplaySize(this@MainActivity).widthPixels / 2)
            }
        })

        tabAdapter.setItemClickListener(object : MainTabMenuAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                tabCurrentPosition = position
                viewPager.setCurrentItem(position, false)
                tabAdapter.updatePosition(position)
                tabMenuLayoutManager.scrollToPositionWithOffset(position, getDisplaySize(this@MainActivity).widthPixels / 2)
            }
        })
    }
}