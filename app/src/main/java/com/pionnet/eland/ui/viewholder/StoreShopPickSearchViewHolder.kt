package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
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
        store.text = data.pickName

        search.setOnClickListener {
            EventBus.fire(HolderEvent(HolderEventType.STORE_SHOP_PICK_SEARCH, data))
        }
    }
}