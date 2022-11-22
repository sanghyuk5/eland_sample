package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.databinding.ViewUnknownModuleBinding
import com.pionnet.eland.utils.debugToast

class UnknownViewHolder(private val binding: ViewUnknownModuleBinding) :
    BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        // NOP
        debugToast(binding.root.context, "Check unknown module: $position")
    }
}