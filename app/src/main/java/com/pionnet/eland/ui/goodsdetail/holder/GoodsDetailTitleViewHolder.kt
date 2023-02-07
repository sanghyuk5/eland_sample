package com.pionnet.eland.ui.goodsdetail.holder

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.pionnet.eland.databinding.ViewCommonGoodsDetailTitleModuleBinding
import com.pionnet.eland.ui.viewholder.BaseViewHolder

class GoodsDetailTitleViewHolder(
    private val binding: ViewCommonGoodsDetailTitleModuleBinding
) : BaseViewHolder(binding.root) {

    override fun onBind(data: Any, position: Int) {
        (data as? ViewEntity)?.let {
            initView(it)
        }
    }

    private fun initView(data: ViewEntity) = with(binding) {
        title.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            height = data.height
            marginStart = data.start
        }

        title.text = data.title

        if (data.subTitle.isNotEmpty()) {
            subTitle.visibility = View.VISIBLE
            subTitle.text = data.subTitle
        } else {
            subTitle.visibility = View.GONE
        }

        if (data.isMoreVisible) {
            more.visibility = View.VISIBLE
            more.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                height = data.height
                marginEnd = data.end
            }

            more.setOnClickListener {

            }
        } else {
            more.visibility = View.GONE
        }

        line.visibility = if (data.isLineVisible) View.VISIBLE else View.GONE
    }
}

data class ViewEntity(
    val height: Int = 0,    // px
    val color: String = "",  // #000000,
    val start: Int = 0,   // px
    val end: Int = 0,      //px
    val title: String = "",
    val subTitle: String = "",
    var isMoreVisible: Boolean = false,
    var moreLinkUrl: String = "",
    val isLineVisible: Boolean = false
)

