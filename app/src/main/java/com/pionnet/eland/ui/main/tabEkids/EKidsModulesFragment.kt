package com.pionnet.eland.ui.main.tabEkids

import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
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
        result.observe(viewLifecycleOwner) {
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
        EventBus.eKidsWeeklyBestTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                viewModel.setTabGoodsView((holderEvent.data ?: "0").toInt(), "weeklyBest")
            }
        }

        EventBus.eKidsNewArrivalTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                viewModel.setTabGoodsView((holderEvent.data ?: "0").toInt(), "newArrival")
            }
        }

        EventBus.eKidsWeeklyExpand.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                viewModel.setExpandableGoodsView(holderEvent.data == "더보기", "weeklyBest")
            }
        }

        EventBus.eKidsNewArrivalExpand.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                viewModel.setExpandableGoodsView(holderEvent.data == "더보기", "newArrival")
            }
        }
    }
}