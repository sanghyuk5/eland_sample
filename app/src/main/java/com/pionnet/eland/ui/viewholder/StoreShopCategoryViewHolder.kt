package com.pionnet.eland.ui.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import com.pionnet.eland.databinding.ViewListBinding
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.main.tabStoreShop.StoreShopCategoryStickyAdapter

class StoreShopCategoryViewHolder(
    private val binding: ViewListBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.StoreShopCategoryData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.StoreShopCategoryData) = with(binding) {
        list.apply {
            layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = StoreShopCategoryStickyAdapter().apply {
                submitList(data.categoryData)
            }
        }
    }
}