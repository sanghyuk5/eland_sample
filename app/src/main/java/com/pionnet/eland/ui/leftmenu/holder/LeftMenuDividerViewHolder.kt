package com.pionnet.eland.ui.leftmenu.holder

import android.graphics.Color
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.orhanobut.logger.Logger
import com.pionnet.eland.databinding.ViewLeftMenuDividerModuleBinding
import com.pionnet.eland.ui.viewholder.BaseViewHolder

class LeftMenuDividerViewHolder(
    private val binding: ViewLeftMenuDividerModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? MarginEntity)?.let {
            initView(it)
        }
    }

    private fun initView(data: MarginEntity) = with(binding) {
        viewDivider.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            height = data.height
            marginStart = data.start
            marginEnd = data.end
        }

        if (data.color.isEmpty()) {
            viewDivider.background = null
        } else {
            viewDivider.setBackgroundColor(Color.parseColor(data.color))
        }
    }
}

data class MarginEntity(
    val height: Int = 0,    // px
    val color: String = "",  // #000000,
    val start: Int = 0,   // px
    val end: Int = 0      //px
)