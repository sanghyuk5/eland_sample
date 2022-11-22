package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class StorePickData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("keyword_result") var keywordResult: Result? = null,
    ) {
        data class Result(
            @SerializedName("search_goods") var goodsList: List<Goods>?
        )
    }
}
