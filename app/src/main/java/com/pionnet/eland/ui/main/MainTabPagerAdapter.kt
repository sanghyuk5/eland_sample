package com.pionnet.eland.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pionnet.eland.model.tabmenu.TabData
import com.pionnet.eland.ui.main.tabHome.HomeModulesFragment
import com.pionnet.eland.ui.main.tabStoreShop.StoreShopModulesFragment

class MainTabPagerAdapter(private val tabs: List<TabData.TabInfo.HeaderIcon>, fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    val fragments = hashMapOf<String?, MainBaseFragment>()

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        val gnbItem = tabs[position]

        val fragment = when (tabs[position].menu_cd) {
            "10" -> {
                HomeModulesFragment()
            }
            "60" -> {
                StoreShopModulesFragment.create("")
            }
            else -> {
                HomeModulesFragment()
            }
        }

        fragments[gnbItem.menu_cd] = fragment

        return fragment
    }
}