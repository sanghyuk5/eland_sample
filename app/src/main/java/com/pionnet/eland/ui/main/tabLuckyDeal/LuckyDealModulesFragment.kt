package com.pionnet.eland.ui.main.tabLuckyDeal

import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class LuckyDealModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: LuckyDealViewModel by viewModels()

    override fun observeData() {
        observeLuckyDeal()
        observeLuckyDealGoods()
    }

    override fun observeTabChange() {
        observeHomeTabChange()
    }

    private fun observeLuckyDeal() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            requestGoodsData()
        }

        tabResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeLuckyDealGoods() = with(viewModel) {
        goodsResult.observe(viewLifecycleOwner) {
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