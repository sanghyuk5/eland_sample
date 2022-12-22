package com.pionnet.eland.ui.main.tabBest

import androidx.fragment.app.viewModels
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class BestModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: BestViewModel by viewModels()

    override fun observeData() {
        observeBest()
    }

    private fun observeBest() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }
}