package com.pionnet.eland.ui.main

import androidx.lifecycle.*
import com.pionnet.eland.SingleLiveEvent

data class RequestRefresh(val isWhole: Boolean = false)
data class ShowToast(val msg: String)

class MainViewModel : ViewModel() {
    val requestRefresh: MutableLiveData<SingleLiveEvent<RequestRefresh>> = MutableLiveData()
    val showToast: MutableLiveData<ShowToast> = MutableLiveData()

//    val preloadTab: MutableLiveData<TabData?> = MutableLiveData()
//    val preloadMain: MutableLiveData<HomeData?> = MutableLiveData()
//
//    fun requestMainGnb() {
//        if (DataManager.isIntroToMain) {
//            viewModelScope.launch {
//                preloadTab.postValue(DataSource.tabData)
//                preloadMain.postValue(DataSource.homeData)
//            }
//        }
//    }
}