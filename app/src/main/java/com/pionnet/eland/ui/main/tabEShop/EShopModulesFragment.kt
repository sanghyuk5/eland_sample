package com.pionnet.eland.ui.main.tabEShop

import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

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
                viewModel.setTabGoodsView((holderEvent.data ?: "0").toInt(), "issue")
            }
        }

        EventBus.eShopArrivalTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                viewModel.setTabGoodsView((holderEvent.data ?: "0").toInt(), "arrival")
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
}