package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.EventBus
import com.pionnet.eland.databinding.ViewStoreShopPickSearchModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class StoreShopPickSearchViewHolder(
    private val binding: ViewStoreShopPickSearchModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopPickSearchData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopPickSearchData) = with(binding) {
        tvStore.text = data.pickName

        tvSearch.setOnClickListener {
            EventBus.fire(data)
        }
    }
}