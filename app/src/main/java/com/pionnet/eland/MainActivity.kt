package com.pionnet.eland

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging
import com.orhanobut.logger.Logger
import com.pionnet.eland.ElandApp.Companion.database
import com.pionnet.eland.data.DataManager.EXTRA_DEEP_LINK
import com.pionnet.eland.data.DataManager.EXTRA_LINK
import com.pionnet.eland.databinding.ActivityMainBinding
import com.pionnet.eland.databinding.ViewItemMainTabmenuBinding
import com.pionnet.eland.data.DataManager.EXTRA_PUSH
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_CONTENT
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_DATE
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_TITLE
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_WEB_LINK
import com.pionnet.eland.data.DataManager.isAppRunning
import com.pionnet.eland.data.TabData
import com.pionnet.eland.data.room.Push
import com.pionnet.eland.ui.main.MainTabPagerAdapter
import com.pionnet.eland.ui.main.MainViewModel
import com.pionnet.eland.ui.main.splash.SplashFragment
import com.pionnet.eland.utils.dialogAlert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
        //getFCMToken()
        isAppRunning = true
    }
    private fun launchMain() {
        initSplash()
        initTopTab()
        initBottomMenu()
        initObserve()
        resolveIntent(intent, true)
        //reload(true)
    }

    private fun getFCMToken(): String?{
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result

            Logger.d("hyuk $token")
        })

        return token
    }

    private fun initSplash() {
        if (!isAppRunning) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, splashFragment, "")
                .commitNow()
        }
    }

    private fun initTopTab() = with(binding) {
        topBar.menu.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LEFT_MENU))
        }

        topBar.search.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH))
        }

        tabPagerAdapter = MainTabPagerAdapter(this@MainActivity)
        viewPager.adapter = tabPagerAdapter

        viewPager.setCurrentItem(0, false)
    }

    private fun initBottomMenu() = with(binding) {
        bottomMenu.searchMenu.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LEFT_MENU))
        }

        bottomMenu.brand.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH, "브랜드"))
        }

        bottomMenu.home.setOnClickListener {
            viewPager.currentItem = 0
        }

        bottomMenu.pushList.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.PUSH_LIST))
        }

        bottomMenu.recentlyGoods.setOnClickListener {}
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

                binding.viewPager.offscreenPageLimit = iconList.size
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

    private fun observeShowToast() {
        viewModel.showToast.observe(this) {
            Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)
        resolveIntent(intent, false)
    }

    private fun resolveIntent(intent: Intent?, fromOnCreate: Boolean) {
        if (intent == null) return

        if (intent.getStringExtra(EXTRA_PUSH).equals("true")) {
            val date = intent.getStringExtra(EXTRA_PUSH_DATE)
            val title = intent.getStringExtra(EXTRA_PUSH_TITLE)
            val content = intent.getStringExtra(EXTRA_PUSH_CONTENT)
            val link = intent.getStringExtra(EXTRA_PUSH_WEB_LINK)
            Logger.d("푸시 value 타이틀 : ${title}, 내용 : ${content}, 링크 : $link")

            CoroutineScope(Dispatchers.Main).launch {
               database.pushDao().insert(Push(date = date ?: "1994-04-04 04:58:52", title = title, content = content, image = null,link = link, false))
            }

            return
        }

        if (intent.getBooleanExtra(EXTRA_DEEP_LINK, false)) {
            EventBus.fire(LinkEvent(intent.getStringExtra(EXTRA_LINK)))
            
            return
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        isAppRunning = false
    }
}