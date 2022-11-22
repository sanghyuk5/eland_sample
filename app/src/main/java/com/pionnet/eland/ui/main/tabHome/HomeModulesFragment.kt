package com.pionnet.eland.ui.main.tabHome

import androidx.fragment.app.viewModels
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class HomeModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: HomeViewModel by viewModels()

    override fun observeData() {
        observeHome()
    }

    private fun observeHome() = with(viewModel) {
        homeResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }
}
