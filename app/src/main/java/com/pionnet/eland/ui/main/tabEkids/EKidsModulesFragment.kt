package com.pionnet.eland.ui.main.tabEkids

import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class EKidsModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: EKidsViewModel by viewModels()

    override fun observeData() {
        observeEKids()
        observeTabChange()
        observeHolderEvent()
    }

    private fun observeEKids() = with(viewModel) {
        eKidsResult.observe(viewLifecycleOwner) {
            //setModules(it)
            setGoodsView()
        }
    }

    override fun observeTabChange() {
        observeEKidsTabChange()
    }

    private fun observeEKidsTabChange() = with(viewModel) {
        tabResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeHolderEvent() {
        EventBus.holderEvent.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                when (holderEvent.type) {
                    HolderEventType.EXPAND_WEEKLY -> viewModel.setExpandableGoodsView(holderEvent.data == "더보기", "weeklyBest")
                    HolderEventType.EXPAND_NEW_ARRIVAL -> viewModel.setExpandableGoodsView(holderEvent.data == "더보기", "newArrival")
                    HolderEventType.TAB_CLICK_WEEKLY -> viewModel.setTabGoodsView((holderEvent.data ?: "0").toInt(), "weeklyBest")
                    HolderEventType.TAB_CLICK_NEW_ARRIVAL -> viewModel.setTabGoodsView((holderEvent.data ?: "0").toInt(), "newArrival")
                }
            }
        }
    }
}