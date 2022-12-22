package com.pionnet.eland.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewCommonFlagItemBinding
import com.pionnet.eland.databinding.ViewCommonFlagsBinding
import com.pionnet.eland.ui.main.StringDiffCallback
import com.pionnet.eland.utils.toPx

class CommonFlagsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle)
{
    private val binding = ViewCommonFlagsBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        with(binding) {
            flagList.apply {
                itemAnimator = null

                adapter = ViewAdapter().apply { submitList(listOf()) }
            }
        }
    }

    private val adapter: ViewAdapter by lazy {
        if (viewType == "black") binding.flagList.addItemDecoration(HorizontalMarginDecoration(2.toPx, 0, 0))
        (binding.flagList.adapter as ViewAdapter)
    }

    var viewType: String = "black"

    var flags: List<String> = listOf()
        get() = adapter.currentList
        set(value) {
            field = value
            adapter.submitList(field)
            adapter.setType(viewType)
        }

    class ViewAdapter : ListAdapter<String, ViewAdapter.ViewHolder>(StringDiffCallback()) {

        private var viewType = ""

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewCommonFlagItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.onBind(getItem(position))
        }

        fun setType(viewType: String) {
            this.viewType = viewType
        }

        inner class ViewHolder(private val binding: ViewCommonFlagItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun onBind(data: String) = with(binding) {
                if (viewType == "black") {
                    tvFlag.setBackgroundColor(Color.parseColor("#131922"))
                } else {
                    tvFlag.setPadding(0,0,5,0)
                    tvFlag.setBackgroundColor(Color.parseColor("#ffffff"))
                    when (data) {
                        "쿠폰제외" -> tvFlag.setTextColor(Color.parseColor("#999999"))
                        "마감임박" -> tvFlag.setTextColor(Color.parseColor("#d7321e"))
                        "해외배송", "무료배송" -> tvFlag.setTextColor(Color.parseColor("#439de9"))
                        "BEST" -> tvFlag.setTextColor(Color.parseColor("#ad181a"))
                        "품절" -> tvFlag.setTextColor(Color.parseColor("#000000"))
                        "온라인단독" -> tvFlag.setTextColor(Color.parseColor("#ee7e8f"))
                        "최저가" -> tvFlag.setTextColor(Color.parseColor("#0262af"))
                        "HIT" -> tvFlag.setTextColor(Color.parseColor("#f4b301"))
                        "SALE" -> tvFlag.setTextColor(Color.parseColor("#fe5a01"))
                        "매장수령" -> tvFlag.setTextColor(Color.parseColor("#a57b53"))
                        "착불" -> tvFlag.setTextColor(Color.parseColor("#a9a9a9"))
                        else -> tvFlag.setTextColor(Color.parseColor("#534f4c"))
                    }
                }

                tvFlag.text = data
            }
        }
    }

    companion object {
        const val COMMON = 0
        const val HOME = 1
    }
}