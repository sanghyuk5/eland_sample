package com.pionnet.eland.ui.viewholder

import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.R
import com.pionnet.eland.databinding.ViewEkidsExpandableModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class EKidsExpandableViewHolder(
    private val binding: ViewEkidsExpandableModuleBinding
) : BaseViewHolder(binding.root) {

    private var isWeeklyBest = false

    init {
        binding.cvMore.setOnClickListener {
            if (isWeeklyBest) {
                EventBus.fire(HolderEvent(HolderEventType.EXPAND_WEEKLY, binding.tvMore.text.toString()))
            } else {
                EventBus.fire(HolderEvent(HolderEventType.EXPAND_NEW_ARRIVAL, binding.tvMore.text.toString()))
            }
        }
    }

    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.EKidsExpandableData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.EKidsExpandableData) = with(binding) {
        isWeeklyBest = data.viewType == "weeklyBest"

        tvMore.text = if (data.isExpand) "더보기" else "닫기"
        var image = root.context.resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24, null)
        if (!data.isExpand) {
            image = root.context.resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24, null)
        }
        tvMore.setCompoundDrawablesWithIntrinsicBounds(null, null, image, null)
    }
}