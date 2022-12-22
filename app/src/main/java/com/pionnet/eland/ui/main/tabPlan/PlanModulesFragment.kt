package com.pionnet.eland.ui.main.tabPlan

import androidx.fragment.app.viewModels
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class PlanModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: PlanViewModel by viewModels()

    override fun observeData() {
        observePlan()
    }

    private fun observePlan() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }
}