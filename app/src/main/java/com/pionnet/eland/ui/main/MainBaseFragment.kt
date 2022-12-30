package com.pionnet.eland.ui.main

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.orhanobut.logger.Logger
import com.pionnet.eland.SingleLiveEvent

abstract class MainBaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    protected var url: String = ""
    protected var params: String = ""

    protected val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            url = it.getString(KEY_ITEM_URL) ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun requestRefresh() {
        mainViewModel.requestRefresh.value = SingleLiveEvent(RequestRefresh())
    }

    open fun onRequestRefresh() {}

    companion object {
        const val KEY_ITEM_TEXT: String = "keyItemText"
        const val KEY_ITEM_PARAMS: String = "keyItemParams"
        const val KEY_ITEM_URL: String = "keyItemUrl"
    }
}