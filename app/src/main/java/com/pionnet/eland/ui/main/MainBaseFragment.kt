package com.pionnet.eland.ui.main

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pionnet.eland.SingleLiveEvent

abstract class MainBaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    private lateinit var tabName: String

    protected var apiUrl: String = ""
    protected var params: String = ""

    protected val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun requestRefresh() {
        mainViewModel.requestRefresh.value = SingleLiveEvent(RequestRefresh())
    }

    open fun onRequestRefresh() {}

    companion object {
        const val KEY_ITEM_PARAMS: String = "keyItemParams"
    }
}