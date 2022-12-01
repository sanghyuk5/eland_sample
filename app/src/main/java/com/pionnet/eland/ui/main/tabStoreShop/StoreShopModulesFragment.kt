package com.pionnet.eland.ui.main.tabStoreShop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.pionnet.eland.EventBus
import com.pionnet.eland.localData.DataManager
import com.pionnet.eland.ui.main.CommonModulesBaseFragment
import com.pionnet.eland.ui.main.ModuleData
import com.pionnet.eland.ui.viewholder.ItemClickIntCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        storeShopResult.observe(viewLifecycleOwner) {
            setModules(it)
            requestStorePickData()
        }

        regularHolderResult.observe(viewLifecycleOwner) {
            setModules(it)
        }

        smartPickHolderResult.observe(viewLifecycleOwner) {
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
                val dlg = SortBottomSheetFragment.newInstance(viewModel.sort)
                dlg.applyCallback = { index ->
                    viewModel.sort = index
                    viewModel.requestStorePickData()
                }
                dlg.show(childFragmentManager, dlg.tag)
            }
        }
    }

    private fun observeViewChange() {
        EventBus.viewChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let {
                viewModel.setView()
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

    override fun setStickyView(isState: Boolean, position: Int) {
        with(binding) {
            val itemIndex = position / (viewModel.categoryGoodsCount + 1) // 타이틀 때문에 +1
            if (!isState) {
                stickyTab.visibility = View.GONE
                return
            } else {
                stickyTab.visibility = View.VISIBLE
                setStickyViewPosition(itemIndex)
            }
        }
    }

    private fun setStickyViewPosition(position: Int) {
        binding.root.postDelayed({
            stickyLayoutManager.scrollToPosition(position)
        }, 5)

        storeShopCategoryStickyAdapter.setSelected(position)
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