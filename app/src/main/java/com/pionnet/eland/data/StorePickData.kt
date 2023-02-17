package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class StorePickData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("keyword_result") val keywordResult: Result? = null,
    ) {
        data class Result(
            @SerializedName("search_goods") val goodsList: List<Goods>?
        )
    }
}
