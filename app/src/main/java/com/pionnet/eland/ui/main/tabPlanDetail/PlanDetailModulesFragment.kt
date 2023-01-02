package com.pionnet.eland.ui.main.tabPlanDetail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
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
        observeSort()
        observeViewChange()
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

    private fun observeSort() {
        EventBus.sort.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                val dlg = SortBottomSheetFragment.newInstance(viewModel.sortPosition, it)
                dlg.applyCallback = { index ->
                    if (index == 0) {
                        linearlayoutManager.scrollToPositionWithOffset(planSortPosition - 1, 0)
                    } else {
                        linearlayoutManager.scrollToPositionWithOffset(planTitlePositionList[index - 1], 0)
                    }

                    viewModel.sortPosition = index
                    binding.stickySort.tvSort.text = viewModel.tabList[index]
                }
                dlg.show(childFragmentManager, dlg.tag)
            }
        }
    }

    private fun observeViewChange() {
        EventBus.viewChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                viewModel.setGoodsView(false)
            }
        }
    }

    private fun initStickyView() = with(binding) {
        tvStickyTitle.visibility = View.VISIBLE
        tvStickyTitle.text = viewModel.shopName

        when (viewModel.viewType) {
            "grid" -> stickySort.ivSort.setImageResource(R.drawable.ic_baseline_grid_view_24)
            "linear" -> stickySort.ivSort.setImageResource(R.drawable.ic_baseline_menu_24)
            "large" -> stickySort.ivSort.setImageResource(R.drawable.ic_baseline_rectangle_24)
        }

        stickySort.tvSort.setOnClickListener {
            EventBus.fire(viewModel.tabList)
        }

        stickySort.ivSort.setOnClickListener {
            EventBus.fire("viewChange")
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
                    stickySort.tvSort.text = viewModel.tabList[goodsData.index + 1]
                    viewModel.sortPosition = goodsData.index + 1
                }
                is ModuleData.CommonGoodsGridData -> {
                    stickySort.tvSort.text = viewModel.tabList[goodsData.index + 1]
                    viewModel.sortPosition = goodsData.index + 1
                }
                is ModuleData.CommonGoodsLargeData -> {
                    stickySort.tvSort.text = viewModel.tabList[goodsData.index + 1]
                    viewModel.sortPosition = goodsData.index + 1
                }
            }
        }
    }
}
