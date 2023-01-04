package com.pionnet.eland.ui.main.tabEShop

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.ui.main.CommonModulesBaseFragment
import com.pionnet.eland.ui.main.tabPlan.PlanModulesFragment

class EShopModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: EShopViewModel by viewModels()

    override fun observeData() {
        observeEShop()
        observeHolderEvent()
    }

    private fun observeEShop() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setView(0)
        }

        tabResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeHolderEvent() = with(viewModel) {
        EventBus.eShopIssueTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                if (holderEvent.data is Int) {
                    viewModel.setTabGoodsView(holderEvent.data, "issue")
                }

            }
        }

        EventBus.eShopArrivalTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                if (holderEvent.data is Int) {
                    viewModel.setTabGoodsView(holderEvent.data, "arrival")
                }
            }
        }

        EventBus.eShopIssueMore.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                viewModel.setExpandableGoodsView("issue")
            }
        }

        EventBus.eShopArrivalMore.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                viewModel.setExpandableGoodsView("arrival")
            }
        }
    }

    companion object {
        fun create(mainApiUrl: String?) =
            EShopModulesFragment().apply {
                arguments = Bundle().apply {
                    if (!mainApiUrl.isNullOrEmpty()) {
                        putString(KEY_ITEM_URL, mainApiUrl)
                    }
                }
            }
    }
}