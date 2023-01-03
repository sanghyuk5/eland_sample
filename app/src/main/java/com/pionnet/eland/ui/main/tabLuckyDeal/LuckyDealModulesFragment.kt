package com.pionnet.eland.ui.main.tabLuckyDeal

import androidx.fragment.app.viewModels
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class LuckyDealModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: LuckyDealViewModel by viewModels()

    override fun observeData() {
        observeLuckyDeal()
        observeLuckyDealGoods()
        observeHolderEvent()
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

    private fun observeHolderEvent() = with(viewModel) {
        EventBus.luckyTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                holderEvent.data?.let { data ->
                    if (data is Int) {
                        setTabGoodsItem(data)
                    }

                }
            }
        }
    }
}