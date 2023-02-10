package com.pionnet.eland.ui.viewholder

import android.graphics.Color
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.pionnet.eland.databinding.ViewCommonDividerModuleBinding
import com.pionnet.eland.ui.main.ModuleData

class CommonDividerViewHolder(
    private val binding: ViewCommonDividerModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? MarginEntity)?.let {
            initView(it)
        }

        (data as? ModuleData.CommonDivider)?.let {
            initView(it.data)
        }
    }

    private fun initView(data: MarginEntity) = with(binding) {
        divider.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            height = data.height
            marginStart = data.start
            marginEnd = data.end
        }

        if (data.color.isEmpty()) {
            divider.background = null
        } else {
            divider.setBackgroundColor(Color.parseColor(data.color))
        }
    }
}

data class MarginEntity(
    val height: Int = 0,    // px
    val color: String = "",  // #000000,
    val start: Int = 0,   // px
    val end: Int = 0      //px
)