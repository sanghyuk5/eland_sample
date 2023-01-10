package com.pionnet.eland.ui.main.tabBest

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment
import com.pionnet.eland.ui.main.tabHome.HomeModulesFragment

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

    private fun observeHolderEvent() {
        EventBus.bestTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                holderEvent.data?.let { data ->
                    if (data is Int) {

                    }
                }
            }
        }
    }

    companion object {
        fun create(mainApiUrl: String?) =
            BestModulesFragment().apply {
                arguments = Bundle().apply {
                    if (!mainApiUrl.isNullOrEmpty()) {
                        putString(KEY_ITEM_URL, mainApiUrl)
                    }
                }
            }
    }
}