package com.pionnet.eland.ui.splash

import androidx.lifecycle.*
import com.pionnet.eland.model.DataSource
import com.pionnet.eland.model.Status
import com.pionnet.eland.utils.addSourceList
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val repository by lazy { SplashRepository() }

    val isTabApiCalled = MutableLiveData<Boolean>()
    val isHomeApiCalled = MutableLiveData<Boolean>()

    /**
     * 탭 정보
     */
    fun requestTabsData() {
        viewModelScope.launch {
            repository.requestTabStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.let { tabData ->
                        DataSource.tabData = tabData
                        //isTabApiCalled.value = true // 그냥 사용
                        isTabApiCalled.postValue(true)
                    }
                } else if (it.status == Status.ERROR) {
                    isTabApiCalled.postValue(false) // 네트워크 스레드에서 사용
                }
            }
        }
    }

    /**
     * 홈 api
     */
    fun requestHomeData() {
        viewModelScope.launch {
            repository.requestHomeStream().collect {
                if (it.status == Status.SUCCESS) {
                    it.data?.let { homeData ->
                        DataSource.homeData = homeData
                        isHomeApiCalled.postValue(true)
                    }
                } else if (it.status == Status.ERROR) {
                    isHomeApiCalled.postValue(false)
                }
            }

        }
    }

    val isReadyToMain = MediatorLiveData<Boolean>().apply {
        addSourceList(isTabApiCalled, isHomeApiCalled) {
            isTabApiCalled.value == true && isHomeApiCalled.value == true
        }
    }
}