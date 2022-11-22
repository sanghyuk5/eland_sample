package com.pionnet.eland.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.databinding.ViewCommonFlagItemBinding
import com.pionnet.eland.databinding.ViewCommonFlagsBinding
import com.pionnet.eland.model.HomeData
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
                addItemDecoration(HorizontalMarginDecoration(2.toPx, 0, 0))
                adapter = ViewAdapter().apply { submitList(listOf()) }
            }
        }
    }

    private val adapter: ViewAdapter by lazy {
        (binding.flagList.adapter as ViewAdapter)
    }

    var flags: List<String> = listOf()
        get() = adapter.currentList
        set(value) {
            field = value
            adapter.submitList(field)
        }

    class ViewAdapter : ListAdapter<String, ViewAdapter.ViewHolder>(DiffCallback()) {

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

        inner class ViewHolder(private val binding: ViewCommonFlagItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun onBind(data: String) = with(binding) {
                flagTxt.text = data
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    companion object {
        const val COMMON = 0
        const val HOME = 1
    }
}