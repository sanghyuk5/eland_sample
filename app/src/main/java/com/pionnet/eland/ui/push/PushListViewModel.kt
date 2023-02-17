package com.pionnet.eland.ui.push

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.data.room.Push
import kotlinx.coroutines.launch

class PushListViewModel : BaseViewModel() {
    private val repository by lazy { PushListRepository() }

    val result = MutableLiveData<List<Push>>()

    init {
        requestData()
    }

    private fun requestData() {
        viewModelScope.launch {
            repository.requestPushListStream().collect {
                Logger.d("hyuk request $it")
                result.postValue(it)
            }
        }
    }
}