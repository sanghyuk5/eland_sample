package com.pionnet.eland.ui.viewholder

import android.view.View
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.databinding.ViewEShopCategoryMoreModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class EShopCategoryMoreViewHolder(
    private val binding: ViewEShopCategoryMoreModuleBinding
) : BaseViewHolder(binding.root) {

    private var isIssue = false

    init {
        binding.more.setOnClickListener {
            if (isIssue) {
                EventBus.fire(HolderEvent(HolderEventType.MORE_E_SHOP_ISSUE))
            } else {
                EventBus.fire(HolderEvent(HolderEventType.MORE_E_SHOP_ARRIVAL))
            }
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.EShopCategoryMoreData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.EShopCategoryMoreData) = with(binding) {
        more.visibility = if (data.isShow) View.VISIBLE else View.GONE
    }
}