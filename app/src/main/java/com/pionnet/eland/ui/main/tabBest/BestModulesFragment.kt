package com.pionnet.eland.ui.main.tabBest

import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class BestModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: BestViewModel by viewModels()

    override fun observeData() {
        observeBest()
        observeHolderEvent()
    }

    private fun observeBest() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeHolderEvent() = with(viewModel) {
        EventBus.bestTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                holderEvent.data?.let { data ->
                    if (data is Int) {

                    }
                }
            }
        }
    }
}