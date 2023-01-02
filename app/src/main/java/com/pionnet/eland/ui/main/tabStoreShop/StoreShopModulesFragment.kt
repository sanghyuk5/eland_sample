package com.pionnet.eland.ui.main.tabStoreShop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment
import com.pionnet.eland.ui.main.ModuleData

class StoreShopModulesFragment : CommonModulesBaseFragment() {

    private var storeShopCategoryTitlePositionList = mutableListOf<Int>()

    private val stickyLayoutManager by lazy { LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false) }
    private val storeShopCategoryStickyAdapter by lazy { StoreShopCategoryStickyAdapter() }

    override val viewModel: StoreShopViewModel by viewModels {
        StoreShopViewModelFactory(params)
    }

    override fun observeData() {
        observeStoreShop()
        observeRegularSearchStore()
        observeStoreShopPick()
        observeSort()
        observeViewChange()
    }

    override fun observeTabChange() {
        observeStoreShopTabChange()
    }

    private fun observeStoreShop() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
            requestStorePickData()
        }

        regularHolderResult.observe(viewLifecycleOwner) {
            setModules(it)
        }

        sortResult.observe(viewLifecycleOwner) {
            setModules(it)

            initStickyView(it)

            storeShopCategoryTitlePositionList.clear()
            val data = recyclerViewAdapter.values.filterIsInstance<ModuleData.StoreShopCategoryTitleData>()
            data.forEach {
                val index = recyclerViewAdapter.values.indexOf(it)
                storeShopCategoryTitlePositionList.add(index)
            }
        }
    }

    private fun observeRegularSearchStore() {
        EventBus.storeShopRegularSearchStore.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                viewModel.requestRegularData()
            }
        }
    }

    private fun observeStoreShopPick() {
        EventBus.storeShopSearchStore.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { data ->
                val dlg = StoreShopPickBottomSheetFragment.newInstance(data, viewModel.pickName)
                dlg.applyCallback = { pickNo, pickName ->
                    viewModel.pickNo = pickNo
                    viewModel.pickName = pickName
                    viewModel.requestStorePickData()
                }
                dlg.show(childFragmentManager, dlg.tag)
            }
        }
    }

    private fun observeSort() {
        EventBus.sort.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                val dlg = SortBottomSheetFragment.newInstance(viewModel.sortPosition, it)
                dlg.applyCallback = { index ->
                    viewModel.sortPosition = index
                    viewModel.requestStorePickData()
                }
                dlg.show(childFragmentManager, dlg.tag)
            }
        }
    }

    private fun observeViewChange() {
        EventBus.viewChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                viewModel.setGoodsView()
            }
        }
    }

    private fun observeStoreShopTabChange() {
        EventBus.tabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { position ->
                linearlayoutManager.scrollToPositionWithOffset(storeShopCategoryTitlePositionList[position], 0)
                setStickyViewPosition(position)
            }
        }
    }

    private fun initStickyView(moduleData: MutableList<ModuleData>) {
        moduleData.forEach { item ->
            when(item) {
                is ModuleData.StoreShopCategoryData -> {
                    binding.stickyTab.adapter = storeShopCategoryStickyAdapter.apply {
                        submitList(item.categoryData)
                    }
                    binding.stickyTab.layoutManager = stickyLayoutManager
                }
            }
        }
    }

    private fun setStickyView(isState: Boolean, position: Int = 0) = with(binding) {
        val itemIndex = position / (viewModel.categoryGoodsCount + 1) // 타이틀 때문에 +1
        if (!isState) {
            stickyTab.visibility = View.GONE
            return
        } else {
            stickyTab.visibility = View.VISIBLE
            setStickyViewPosition(itemIndex)
        }
    }

    private fun setStickyViewPosition(position: Int) {
        binding.root.postDelayed({
            stickyLayoutManager.scrollToPosition(position)
        }, 5)

        storeShopCategoryStickyAdapter.setSelected(position)
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

            val storeShopCategoryDataPosition = (recyclerViewAdapter.values.indexOfFirst { it is ModuleData.StoreShopCategoryData })
            val storeShopCategoryTitleDataPosition = (recyclerViewAdapter.values.indexOfFirst { it is ModuleData.StoreShopCategoryTitleData })

            if (recyclerViewAdapter.values.isNotEmpty() && firstVisiblePosition != -1) {
                if (storeShopCategoryDataPosition != -1 && storeShopCategoryDataPosition < firstVisiblePosition) {
                    val stickyIndex = firstVisiblePosition - storeShopCategoryTitleDataPosition
                    setStickyView(true, stickyIndex) // sticky visible
                } else {
                    setStickyView(false)  // sticky gone
                }
            }
        }
    }

    class StoreShopViewModelFactory(private val params: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoreShopViewModel(params) as T
        }
    }

    companion object {
        fun create(param: String) =
            StoreShopModulesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_PARAMS, param)
                }
            }
    }
}