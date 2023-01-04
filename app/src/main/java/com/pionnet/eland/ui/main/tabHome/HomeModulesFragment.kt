package com.pionnet.eland.ui.main.tabHome

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class HomeModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: HomeViewModel by viewModels()

    override fun observeData() {
        observeHome()
        observeHolderEvent()
    }

    private fun observeHome() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeHolderEvent() = with(viewModel) {
        EventBus.homeTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                holderEvent.data?.let { data ->
                    if (data is Int) {
                        setTabGoodsItem(data)
                    }
                }
            }
        }
    }

    companion object {
        fun create(mainApiUrl: String?) =
            HomeModulesFragment().apply {
                arguments = Bundle().apply {
                    if (!mainApiUrl.isNullOrEmpty()) {
                        putString(KEY_ITEM_URL, mainApiUrl)
                    }
                }
            }
    }
}
