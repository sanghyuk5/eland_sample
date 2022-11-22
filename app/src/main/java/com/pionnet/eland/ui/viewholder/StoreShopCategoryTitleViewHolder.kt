package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewStoreShopCategoryTitleModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class StoreShopCategoryTitleViewHolder(
    private val binding: ViewStoreShopCategoryTitleModuleBinding
) : BaseViewHolder(binding.root) {
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopCategoryTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopCategoryTitleData) = with(binding) {
        tvTitle.text = data.title
    }
}