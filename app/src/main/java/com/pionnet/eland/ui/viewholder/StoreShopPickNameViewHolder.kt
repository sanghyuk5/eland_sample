package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewStoreShopPickNameModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class StoreShopPickNameViewHolder(
    private val binding: ViewStoreShopPickNameModuleBinding
) : BaseViewHolder(binding.root) {

    private var pickNo: String? = null

    init {
        binding.tvStore.setOnClickListener {

        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopSmartPickNameData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopSmartPickNameData) = with(binding) {
        pickNo = data.pickNo

        tvStore.text = data.pickName + " 전체상품보기>"
    }
}