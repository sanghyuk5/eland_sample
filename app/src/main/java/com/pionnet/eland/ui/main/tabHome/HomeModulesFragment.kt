package com.pionnet.eland.ui.main.tabHome

import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class HomeModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: HomeViewModel by viewModels()

    override fun observeData() {
        observeHome()
    }

    override fun observeTabChange() {
        observeHomeTabChange()
    }

    private fun observeHome() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
        }

        tabResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeHomeTabChange() = with(viewModel) {
        EventBus.tabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { position ->
                setTabGoodsItem(position)
            }
        }
    }
}
