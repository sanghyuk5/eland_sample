package com.pionnet.eland.ui.main

import androidx.lifecycle.ViewModel

abstract class CommonViewModel: ViewModel() {
    abstract fun requestData()
}