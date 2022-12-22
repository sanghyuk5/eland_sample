package com.pionnet.eland.ui.main.tabLuckyDeal

import androidx.fragment.app.viewModels
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class LuckyDealModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: LuckyDealViewModel by viewModels()

    override fun observeData() {
        observeLuckyDeal()
        observeLuckyDealGoods()
    }

    private fun observeLuckyDeal() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            requestGoodsData()
        }
    }

    private fun observeLuckyDealGoods() = with(viewModel) {
        goodsResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }
}