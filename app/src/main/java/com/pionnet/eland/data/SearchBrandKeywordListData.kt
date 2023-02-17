package com.pionnet.eland.data


import com.google.gson.annotations.SerializedName

data class SearchBrandKeywordListData(
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: List<List<Data?>?>?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("nav_brand_keyword_title") val navBrandKeywordTitle: String?,
        @SerializedName("nav_brand_keyword_list") val navBrandKeywordList: List<NavBrandKeyword?>?
    ) {
        data class NavBrandKeyword(
            @SerializedName("brand_nm") val brandNm: String?
        )
    }
}