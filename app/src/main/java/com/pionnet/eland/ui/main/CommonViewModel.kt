package com.pionnet.eland.ui.main

import com.pionnet.eland.BaseViewModel

abstract class CommonViewModel: BaseViewModel() {
    abstract fun requestData()
}