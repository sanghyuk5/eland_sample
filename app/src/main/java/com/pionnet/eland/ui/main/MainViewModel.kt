package com.pionnet.eland.ui.main

import androidx.lifecycle.*
import com.pionnet.eland.SingleLiveEvent
import com.pionnet.eland.data.TabData
import com.pionnet.eland.utils.addSourceList
import kotlinx.coroutines.launch

data class RequestRefresh(val isWhole: Boolean = false)
data class ShowToast(val msg: String)

class MainViewModel : ViewModel() {

    /**
     * splash 에서 사용
     */
    private val repository by lazy { MainRepository() }

    val tabResult = MutableLiveData<TabData>()

    private val isTabApiCalled = MutableLiveData<Boolean>()
    private val isHomeApiCalled = MutableLiveData<Boolean>()

    val isSplashError = MutableLiveData<Boolean>()

    val isReadyToMain = MediatorLiveData<Boolean>().apply {
        addSourceList(isTabApiCalled, isHomeApiCalled) {
            isTabApiCalled.value == true && isHomeApiCalled.value == true
        }
    }

    fun requestData() {
        viewModelScope.launch {
            repository.requestTabStream().collect {
                it.fold(
                    onSuccess = {
                        tabResult.postValue(it)
                        isTabApiCalled.postValue(true)
                    },
                    onFailure = {
                        isSplashError.postValue(false)
                    }
                )
            }

            repository.requestHomeStream().collect {
                it.fold(
                    onSuccess = {
                        isHomeApiCalled.postValue(true)
                    },
                    onFailure = {
                        isSplashError.postValue(false)
                    }
                )
            }
        }
    }

    /**
     * main 에서 사용
     */
    val requestRefresh: MutableLiveData<SingleLiveEvent<RequestRefresh>> = MutableLiveData()
    val showToast: MutableLiveData<ShowToast> = MutableLiveData()
}