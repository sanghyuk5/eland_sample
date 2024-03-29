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
        binding.moreView.setOnClickListener {
            if (isWeeklyBest) {
                EventBus.fire(HolderEvent(HolderEventType.EXPAND_E_KIDS_WEEKLY, binding.more.text.toString()))
            } else {
                EventBus.fire(HolderEvent(HolderEventType.EXPAND_E_KIDS_ARRIVAL, binding.more.text.toString()))
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

        more.text = if (data.isExpand) "더보기" else "닫기"
        var image = root.context.resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24, null)
        if (!data.isExpand) {
            image = root.context.resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24, null)
        }
        more.setCompoundDrawablesWithIntrinsicBounds(null, null, image, null)
    }
}