package com.pionnet.eland.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.orhanobut.logger.Logger
import com.pionnet.eland.HolderEvent
import com.pionnet.eland.R
import com.pionnet.eland.SingleLiveEvent
import com.pionnet.eland.databinding.FragmentMainCommonModulesBinding
import com.pionnet.eland.localData.DataManager

data class FooterAction(val position: Int, val isExpand: Boolean)

/**
 * A Base Fragment for Modules
 */
abstract class CommonModulesBaseFragment :
    MainBaseFragment(R.layout.fragment_main_common_modules) {

    protected val binding by viewBinding(FragmentMainCommonModulesBinding::bind)

    protected val recyclerViewAdapter by lazy {
        binding.list.adapter as CommonModulesRecyclerViewAdapter
    }

    protected val linearlayoutManager by lazy { LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false) }

    private var isLoadingFinished = false
    private var isLoading = false

    abstract val viewModel: CommonViewModel

//    final override fun canScrollUp(): Boolean {
//        return binding.list.canScrollVertically(-1)
//    }
//
//    final override fun scrollToTop() {
//        (binding.list.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(0, 0)
//    }

    override fun onPause() {
        super.onPause()
    }

    override fun onRequestRefresh() {
        super.onRequestRefresh()
        viewModel.requestData()
    }

    open fun onRequestedData() {
        isLoading = true
    }


    abstract fun observeData()

    protected fun addScrollListener(listener: RecyclerView.OnScrollListener) {
        binding.list.addOnScrollListener(listener)
    }

    protected fun removeScrollListener(listener: RecyclerView.OnScrollListener) {
        binding.list.removeOnScrollListener(listener)
    }

    protected fun setModules(moduleList: MutableList<ModuleData>) {
        recyclerViewAdapter.apply {
            moduleList.forEach {
                Logger.t("####").d("setModules > ${it.javaClass.simpleName}")
            }
            values = moduleList
        }
    }

    protected fun showToast(msg: String) {
        mainViewModel.showToast.value = ShowToast(msg)
    }

    open fun onBaseViewCreated() {}

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBaseViewCreated()

        observeData()

        with(viewModel) {
            requestData()
        }

        with(binding) {
            swipeRefresh.setDistanceToTriggerSync(300 * Resources.getSystem().displayMetrics.density.toInt())
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                requestRefresh()
            }

            list.apply {
                setHasFixedSize(true)
                layoutManager = linearlayoutManager
                adapter = CommonModulesRecyclerViewAdapter(
                    this@CommonModulesBaseFragment
                ).apply {
                    registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                            super.onItemRangeChanged(positionStart, itemCount)
                            Logger.d("onItemRangeChanged start: $positionStart, count: $itemCount")
                        }

                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            super.onItemRangeInserted(positionStart, itemCount)
                            Logger.d("onItemRangeInserted start: $positionStart, count: $itemCount")
                        }

                        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                            super.onItemRangeRemoved(positionStart, itemCount)
                            Logger.d("onItemRangeRemoved start: $positionStart, count: $itemCount")
                        }

                        override fun onItemRangeChanged(
                            positionStart: Int,
                            itemCount: Int,
                            payload: Any?
                        ) {
                            super.onItemRangeChanged(positionStart, itemCount, payload)
                            Logger.d("onItemRangeChanged start: $positionStart, count: $itemCount, payload: $payload")
                        }

                        override fun onItemRangeMoved(
                            fromPosition: Int,
                            toPosition: Int,
                            itemCount: Int
                        ) {
                            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                            Logger.d("onItemRangeMoved from: $fromPosition, to : $toPosition, count: $itemCount")
                        }
                    })
                }
            }
        }
    }
}
