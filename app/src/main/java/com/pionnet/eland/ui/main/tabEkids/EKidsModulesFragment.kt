package com.pionnet.eland.ui.main.tabEkids

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class EKidsModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: EKidsViewModel by viewModels()

    override fun observeData() {
        observeEKids()
        observeHolderEvent()
    }

    private fun observeEKids() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setGoodsView()
        }
        tabResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeHolderEvent() {
        EventBus.eKidsWeeklyBestTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                if (holderEvent.data is Int) {
                    viewModel.setTabGoodsView(holderEvent.data, "weeklyBest")
                }
            }
        }

        EventBus.eKidsNewArrivalTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                if (holderEvent.data is Int) {
                    viewModel.setTabGoodsView(holderEvent.data, "newArrival")
                }
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

    companion object {
        fun create(mainApiUrl: String?) =
            EKidsModulesFragment().apply {
                arguments = Bundle().apply {
                    if (!mainApiUrl.isNullOrEmpty()) {
                        putString(KEY_ITEM_URL, mainApiUrl)
                    }
                }
            }
    }
}