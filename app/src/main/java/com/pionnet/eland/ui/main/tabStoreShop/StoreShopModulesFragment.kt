package com.pionnet.eland.ui.main.tabStoreShop

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pionnet.eland.ui.main.CommonModulesBaseFragment

class StoreShopModulesFragment : CommonModulesBaseFragment() {
    override val viewModel: StoreShopViewModel by viewModels {
        StoreShopViewModelFactory(params)
    }

    override fun observeData() {
        observeStoreShop()
    }

    private fun observeStoreShop() = with(viewModel) {
        storeShopResult.observe(viewLifecycleOwner) {
            setModules(it)
            requestStorePickData()
        }

        storePickResult.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    class StoreShopViewModelFactory(private val params: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoreShopViewModel(params) as T
        }
    }

    companion object {
        fun create(param: String) =
            StoreShopModulesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_PARAMS, param)
                }
            }
    }
}