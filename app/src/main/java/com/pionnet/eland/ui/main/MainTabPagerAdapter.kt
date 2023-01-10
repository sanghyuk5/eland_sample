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

class MainTabPagerAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    val fragments = hashMapOf<String?, MainBaseFragment>()
    private var tabs = listOf <TabData.TabInfo.HeaderIcon>()

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        val gnbItem = tabs[position]

        val fragment = when (gnbItem.menu_cd) {
            "10" -> HomeModulesFragment.create(gnbItem.api_url)
            "20" -> LuckyDealModulesFragment.create(gnbItem.api_url)
            "30" -> BestModulesFragment.create(gnbItem.api_url)
            "40" -> PlanModulesFragment.create(gnbItem.api_url)
            "60" -> StoreShopModulesFragment.create("", gnbItem.api_url)
            "80" -> MainWebViewFragment.create(tabs[position].link_url)
            "110" -> EKidsModulesFragment.create(gnbItem.api_url)
            "120" -> EShopModulesFragment.create(gnbItem.api_url)
            "130" -> PlanDetailModulesFragment.create(gnbItem.api_url)
            else -> HomeModulesFragment.create(gnbItem.api_url)
        }

        fragments[gnbItem.menu_cd] = fragment

        return fragment
    }

    fun setData(tabs: List<TabData.TabInfo.HeaderIcon>) {
        this.tabs = tabs
        notifyDataSetChanged()
    }
}