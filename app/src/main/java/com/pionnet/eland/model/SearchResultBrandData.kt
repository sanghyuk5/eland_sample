package com.pionnet.eland.model


import com.google.gson.annotations.SerializedName

data class SearchResultBrandData(
    @SerializedName("result") val result: List<Result>?,
    @SerializedName("code") val code: String?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Result(
        @SerializedName("img_path") val imgPath: String?,
        @SerializedName("keyword") val keyword: String?,
        @SerializedName("brand_url") val brandUrl: String?
    )
}