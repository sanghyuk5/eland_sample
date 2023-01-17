package com.pionnet.eland.ui.search.searchCamera

import androidx.lifecycle.viewModelScope
import com.pionnet.eland.ui.main.CommonViewModel
import kotlinx.coroutines.launch

class SearchCameraViewModel : CommonViewModel() {

    private val repository by lazy { SearchCameraRepository() }

    override fun requestData() {
        viewModelScope.launch {

        }
    }
}