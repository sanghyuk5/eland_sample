package com.pionnet.eland.ui.goodsdetail.question

import androidx.lifecycle.MutableLiveData
import com.pionnet.eland.BaseViewModel
import com.pionnet.eland.model.*
import com.pionnet.eland.ui.viewholder.MarginEntity
import com.pionnet.eland.utils.toPx

class GoodsDetailQuestionViewModel : BaseViewModel() {

    val result = MutableLiveData<List<GoodsDetailData.Data.GoodsQuestionInfo.Question?>>()

    fun createModules(data: GoodsDetailData.Data.GoodsQuestionInfo) {
        var questionList = listOf<GoodsDetailData.Data.GoodsQuestionInfo.Question?>()

        if (!data.questionList.isNullOrEmpty()) {
            questionList = data.questionList

        }

        result.postValue(questionList)
    }
}