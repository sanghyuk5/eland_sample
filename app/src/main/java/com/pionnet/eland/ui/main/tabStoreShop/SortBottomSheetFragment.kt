package com.pionnet.eland.ui.main.tabStoreShop

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentBottomSheetBinding
import com.pionnet.eland.databinding.ViewItemBottomSheetBinding
import com.pionnet.eland.localData.DataManager.ARG_LIST
import com.pionnet.eland.model.BaseParcelable
import com.pionnet.eland.ui.viewholder.ItemClickIntCallback
import com.pionnet.eland.views.BaseBottomSheetDialogFragment

private const val ARG_SORT = "sort"

class SortBottomSheetFragment : BaseBottomSheetDialogFragment() {

    override val dialogResourceId: Int
        get() = R.layout.fragment_bottom_sheet

    private val binding by viewBinding(FragmentBottomSheetBinding::bind)

    override val shouldCancellableOnTouchOutside = true
    override val shouldFixedMaxHeight: Boolean = true
    override val isDraggable: Boolean = true

    override fun offsetHeight(): Int = 300

    private lateinit var sortData: Array<String>

    private var selectedPosition = 1
    private var data: List<String>? = null

    private val itemClickCallback: ItemClickIntCallback = { index ->
        applyCallback?.invoke(index)
        dismiss()
    }

    var applyCallback: ((Int) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedPosition = it.getInt(ARG_SORT)
            data = it.getParcelable<BaseParcelable>(ARG_LIST)?.value as? List<String>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvTitle.text = "정렬"

            data?.let {
                sortData = if (it.isEmpty()) {
                    resources.getStringArray(R.array.sort_list)
                } else {
                    it.toTypedArray()
                }
            }

            rvItem.apply {
                adapter = SortPickAdapter(sortData, itemClickCallback)
            }

            flTop.setOnClickListener {
                dismiss()
            }

            tvClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private inner class SortPickAdapter(val item: Array<String>, private val itemClickCallback: ItemClickIntCallback)
        : RecyclerView.Adapter<SortPickAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ViewItemBottomSheetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(item[position], position)
            holder.itemView.setOnClickListener {
                itemClickCallback.invoke(position)
            }
        }

        private inner class ViewHolder(val binding: ViewItemBottomSheetBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: String, position: Int) = with(binding) {
                tvName.text = data
                if (selectedPosition == position) {
                    ivCheck.visibility = View.VISIBLE
                    tvName.setTypeface(null, Typeface.BOLD)
                    tvName.setTextColor(Color.parseColor("#2196F3"))
                } else {
                    ivCheck.visibility = View.GONE
                    tvName.setTypeface(null, Typeface.NORMAL)
                    tvName.setTextColor(Color.parseColor("#2f2f2f"))
                }
            }
        }

        override fun getItemCount(): Int = item.size
    }

    companion object {
        @JvmStatic
        fun newInstance(sort: Int, data: List<String>) =
            SortBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SORT, sort)
                    putParcelable(ARG_LIST, BaseParcelable(data))
                }
            }
    }
}