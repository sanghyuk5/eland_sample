package com.pionnet.eland.ui.search

import androidx.lifecycle.viewModelScope
import com.pionnet.eland.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    private val repository by lazy { SearchRepository() }

    fun requestData() {
        viewModelScope.launch {

        }
    }
}