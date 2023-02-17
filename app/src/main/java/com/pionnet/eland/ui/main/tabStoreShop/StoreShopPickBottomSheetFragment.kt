package com.pionnet.eland.ui.main.tabStoreShop

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pionnet.eland.R
import com.pionnet.eland.databinding.FragmentBottomSheetBinding
import com.pionnet.eland.databinding.ViewItemBottomSheetBinding
import com.pionnet.eland.data.BaseParcelable
import com.pionnet.eland.data.StoreShopData
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.viewholder.ItemClickIntCallback
import com.pionnet.eland.views.BaseBottomSheetDialogFragment

private const val ARG_STORE_PICK = "store_pick"
private const val ARG_STORE_PICK_NAME = "store_pick_name"

class StoreShopPickBottomSheetFragment : BaseBottomSheetDialogFragment() {

    private var data: List<StoreShopData.Data.SmartPick>? = null

    override val dialogResourceId: Int
        get() = R.layout.fragment_bottom_sheet

    private val binding by viewBinding(FragmentBottomSheetBinding::bind)

    override val shouldCancellableOnTouchOutside = true
    override val shouldFixedMaxHeight: Boolean = true
    override val isDraggable: Boolean = true

    override fun offsetHeight(): Int = 300

    private var pickNo = ""
    private var pickName = ""

    private val itemClickCallback: ItemClickIntCallback = { index ->
        pickNo = data?.get(index)?.categoryNo ?: ""
        pickName = data?.get(index)?.name ?: ""

        data?.select(index)
    }

    var applyCallback: ((String, String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable<BaseParcelable>(ARG_STORE_PICK)?.value as? List<StoreShopData.Data.SmartPick>
            pickName = it.getString(ARG_STORE_PICK_NAME) ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvTitle.text = "스토어픽 지점"

            rvItem.apply {
                adapter = StorePickAdapter(itemClickCallback).apply {
                    data?.let { smartPick ->
                        submitList(smartPick)
                    }
                }
            }

            flTop.setOnClickListener {
                dismiss()
            }

            tvClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun List<StoreShopData.Data.SmartPick>.select(index: Int) {
        val data = this.map { it.copy() }.toMutableList()
        val selectedItem = data.indexOfFirst { it.isSelected }
        if (selectedItem != -1 && selectedItem != index) {
            data.getOrNull(selectedItem)?.isSelected = false
            data.getOrNull(index)?.isSelected = true

            applyCallback?.invoke(pickNo, pickName)
            dismiss()
        }
    }

    private inner class StorePickAdapter(private val itemClickCallback: ItemClickIntCallback)
        : ListAdapter<StoreShopData.Data.SmartPick, StorePickAdapter.ViewHolder>(DiffCallback()) {

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
            holder.bind(currentList[position])
            holder.itemView.setOnClickListener {
                itemClickCallback.invoke(position)
            }
        }

        private inner class ViewHolder(val binding: ViewItemBottomSheetBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(data: StoreShopData.Data.SmartPick) = with(binding) {
                tvName.text = data.name
                if (data.isSelected) {
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
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<StoreShopData.Data.SmartPick>() {
        override fun areItemsTheSame(oldItem: StoreShopData.Data.SmartPick, newItem: StoreShopData.Data.SmartPick): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: StoreShopData.Data.SmartPick, newItem: StoreShopData.Data.SmartPick): Boolean =
            oldItem == newItem
    }

    companion object {
        @JvmStatic
        fun newInstance(data: ModuleData.StoreShopPickSearchData, pickName: String) =
            StoreShopPickBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_STORE_PICK, BaseParcelable(data.smartPickData))
                    putString(ARG_STORE_PICK_NAME, pickName)
                }
            }
    }
}