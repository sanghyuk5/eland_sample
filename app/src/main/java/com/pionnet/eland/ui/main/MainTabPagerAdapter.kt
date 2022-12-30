package com.pionnet.eland.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pionnet.eland.model.TabData
import com.pionnet.eland.ui.main.tabBest.BestModulesFragment
import com.pionnet.eland.ui.main.tabEkids.EKidsModulesFragment
import com.pionnet.eland.ui.main.tabHome.HomeModulesFragment
import com.pionnet.eland.ui.main.tabLuckyDeal.LuckyDealModulesFragment
import com.pionnet.eland.ui.main.tabEShop.EShopModulesFragment
import com.pionnet.eland.ui.main.tabPlan.PlanModulesFragment
import com.pionnet.eland.ui.main.tabPlanDetail.PlanDetailModulesFragment
import com.pionnet.eland.ui.main.tabStoreShop.StoreShopModulesFragment
import com.pionnet.eland.ui.main.tabWeb.MainWebViewFragment

class MainTabPagerAdapter(private val tabs: List<TabData.TabInfo.HeaderIcon>, fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    val fragments = hashMapOf<String?, MainBaseFragment>()

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        val gnbItem = tabs[position]

        val fragment = when (tabs[position].menu_cd) {
            "10" -> HomeModulesFragment()
            "20" -> LuckyDealModulesFragment()
            "30" -> BestModulesFragment()
            "40" -> PlanModulesFragment()
            "60" -> StoreShopModulesFragment.create("")
            "80" -> MainWebViewFragment.create(tabs[position].link_url)
            "110" -> EKidsModulesFragment()
            "120" -> EShopModulesFragment()
            "130" -> PlanDetailModulesFragment()
            else -> HomeModulesFragment()
        }

        fragments[gnbItem.menu_cd] = fragment

        return fragment
    }
}