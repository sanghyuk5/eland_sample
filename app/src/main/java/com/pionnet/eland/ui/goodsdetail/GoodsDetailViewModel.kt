package com.pionnet.eland.ui.goodsdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.data.*
import kotlinx.coroutines.launch

class GoodsDetailViewModel : BaseViewModel() {
    private val repository by lazy { GoodsDetailRepository() }

    val result = MutableLiveData<GoodsDetailData.Data>()

    var reviewCount = 0
    var questionCount = 0

    fun requestData() {
        viewModelScope.launch {
            repository.requestGoodsDetailStream().collect {
                it.fold(
                    onSuccess = {
                       it?.data?.let { data ->
                           reviewCount = data.goodsInfo?.goodsReviewInfo?.reviewCount ?: 0
                           questionCount = data.goodsInfo?.goodsQuestionInfo?.questionCount ?: 0

                           result.postValue(data)
                       }
                    },
                    onFailure = {}
                )
            }
        }
    }
}