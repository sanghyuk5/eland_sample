package com.pionnet.eland.ui.main.tabPlanDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.HolderEventType
import com.pionnet.eland.R
import com.pionnet.eland.ui.main.CommonModulesBaseFragment
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.main.tabStoreShop.SortBottomSheetFragment

class PlanDetailModulesFragment : CommonModulesBaseFragment() {

    private var planTitlePositionList = mutableListOf<Int>()
    private var planSortPosition = 0

    override val viewModel: PlanDetailViewModel by viewModels()

    override fun observeData() {
        observePlanDetail()
        observeHolderEvent()
    }

    private fun observePlanDetail() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
            setGoodsView(true)
        }

        sortResult.observe(viewLifecycleOwner) {
            setModules(it)

            initStickyView()

            planTitlePositionList.clear()
            val data = recyclerViewAdapter.values.filterIsInstance<ModuleData.PlanTabTitleData>()
            data.forEach {
                val index = recyclerViewAdapter.values.indexOf(it)
                planTitlePositionList.add(index)
            }
        }
    }

    private fun observeHolderEvent() {
        EventBus.planDetailSort.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                if (holderEvent.data is List<*>) {
                    val data = holderEvent.data.filterIsInstance<String>()
                    val dlg = SortBottomSheetFragment.newInstance(viewModel.sortPosition, data)
                    dlg.applyCallback = { index ->
                        if (index == 0) {
                            linearlayoutManager.scrollToPositionWithOffset(planSortPosition - 1, 0)
                        } else {
                            linearlayoutManager.scrollToPositionWithOffset(planTitlePositionList[index - 1], 0)
                        }

                        viewModel.sortPosition = index
                        binding.stickySort.name.text = viewModel.tabList[index]
                    }
                    dlg.show(childFragmentManager, dlg.tag)
                }
            }
        }

        EventBus.planDetailViewChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                viewModel.setGoodsView(false)
            }
        }
    }

    private fun initStickyView() = with(binding) {
        stickyTitle.visibility = View.VISIBLE
        stickyTitle.text = viewModel.shopName

        when (viewModel.viewShape) {
            "grid" -> stickySort.image.setImageResource(R.drawable.ic_baseline_grid_view_24)
            "linear" -> stickySort.image.setImageResource(R.drawable.ic_baseline_menu_24)
            "large" -> stickySort.image.setImageResource(R.drawable.ic_baseline_rectangle_24)
        }

        stickySort.sortView.setOnClickListener {
            EventBus.fire(HolderEvent(HolderEventType.PLAN_DETAIL_SORT, viewModel.tabList))
        }

        stickySort.sort.setOnClickListener {
            EventBus.fire(HolderEvent(HolderEventType.PLAN_DETAIL_VIEW_CHANGE))
        }
    }

    override fun onResume() {
        super.onResume()
        addScrollListener(onScrollListener)
    }

    override fun onPause() {
        super.onPause()
        removeScrollListener(onScrollListener)
    }

    private val onScrollListener: RecyclerView.OnScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val firstVisiblePosition =
                (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

            planSortPosition = (recyclerViewAdapter.values.indexOfFirst { it is ModuleData.CommonSortData })

            if (recyclerViewAdapter.values.isNotEmpty() && firstVisiblePosition != -1) {
                if (planSortPosition != -1 && planSortPosition < firstVisiblePosition) {
                    setStickyView(true, firstVisiblePosition) // sticky visible
                } else {
                    setStickyView(false)  // sticky gone
                }
            }
        }
    }

    private fun setStickyView(isState: Boolean, position: Int = 0) = with(binding) {
        if (!isState) {
            stickySort.root.visibility = View.GONE
            return
        } else {
            stickySort.root.visibility = View.VISIBLE

            when (val goodsData = recyclerViewAdapter.values[position]) {
                is ModuleData.CommonGoodsLinearData -> {
                    stickySort.name.text = viewModel.tabList[goodsData.index + 1]
                    viewModel.sortPosition = goodsData.index + 1
                }
                is ModuleData.CommonGoodsGridData -> {
                    stickySort.name.text = viewModel.tabList[goodsData.index + 1]
                    viewModel.sortPosition = goodsData.index + 1
                }
                is ModuleData.CommonGoodsLargeData -> {
                    stickySort.name.text = viewModel.tabList[goodsData.index + 1]
                    viewModel.sortPosition = goodsData.index + 1
                }
            }
        }
    }

    companion object {
        fun create(mainApiUrl: String?) =
            PlanDetailModulesFragment().apply {
                arguments = Bundle().apply {
                    if (!mainApiUrl.isNullOrEmpty()) {
                        putString(KEY_ITEM_URL, mainApiUrl)
                    }
                }
            }
    }
}
