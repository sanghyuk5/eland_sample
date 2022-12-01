package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewStoreShopCategoryModuleBinding
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.main.tabStoreShop.StoreShopCategoryStickyAdapter

class StoreShopCategoryViewHolder(
    private val binding: ViewStoreShopCategoryModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopCategoryData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopCategoryData) = with(binding) {
        rvCategory.apply {
            adapter = StoreShopCategoryStickyAdapter().apply {
                submitList(data.categoryData)
            }
        }
    }
}