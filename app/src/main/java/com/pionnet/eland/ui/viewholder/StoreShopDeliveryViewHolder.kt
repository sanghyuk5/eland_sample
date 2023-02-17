package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewStoreShopDeliveryModuleBinding
import com.pionnet.eland.data.StoreShopData
import com.pionnet.eland.ui.main.ModuleData

class StoreShopDeliveryViewHolder(
    private val binding: ViewStoreShopDeliveryModuleBinding
) : BaseViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {

        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopDeliveryData)?.let {
            initView(it.deliveryData)
        }
    }

    private fun initView(data: StoreShopData.Data.Delivery) = with(binding) {
        delivery.text = data.addr
    }
}