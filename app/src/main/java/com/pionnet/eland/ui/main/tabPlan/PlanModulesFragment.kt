package com.pionnet.eland.ui.main.tabPlan

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.pionnet.eland.EventBus
import com.pionnet.eland.ui.main.CommonModulesBaseFragment
import com.pionnet.eland.ui.main.tabHome.HomeModulesFragment

class PlanModulesFragment : CommonModulesBaseFragment() {

    override val viewModel: PlanViewModel by viewModels()

    override fun observeData() {
        observePlan()
        observeHolderEvent()
    }

    private fun observePlan() = with(viewModel) {
        result.observe(viewLifecycleOwner) {
            setModules(it)
        }
    }

    private fun observeHolderEvent() = with(viewModel) {
        EventBus.planTabChange.observe(viewLifecycleOwner) {
            it.getIfNotHandled()?.let { holderEvent ->
                if (holderEvent.data is Int) {
                    setTabGoodsItem(holderEvent.data)
                }
            }
        }
    }

    private fun onLoadMore() {
        viewModel.loadMore()
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

            if (dy > 0) {
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition >= itemTotalCount) {
                    onLoadMore()
                }
            }
        }
    }

    companion object {
        fun create(mainApiUrl: String?) =
            PlanModulesFragment().apply {
                arguments = Bundle().apply {
                    if (!mainApiUrl.isNullOrEmpty()) {
                        putString(KEY_ITEM_URL, mainApiUrl)
                    }
                }
            }
    }
}