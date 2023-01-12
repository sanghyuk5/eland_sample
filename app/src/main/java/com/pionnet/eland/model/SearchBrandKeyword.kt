package com.pionnet.eland.model


import com.google.gson.annotations.SerializedName

data class SearchBrandKeyword(
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: List<Data?>?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("nav_brand_keyword") val navBrandKeyword: NavBrandKeyword?
    ) {
        data class NavBrandKeyword(
            @SerializedName("nav_brand_keyword_list_kor") val navBrandKeywordListKor: List<NavBrandKeywordLetter?>?,
            @SerializedName("nav_brand_keyword_list_eng") val navBrandKeywordListEng: List<NavBrandKeywordLetter?>?
        ) {
            data class NavBrandKeywordLetter(
                @SerializedName("nav_brand_keyword_count") val navBrandKeywordCount: Int?,
                @SerializedName("nav_brand_keyword_title") val navBrandKeywordTitle: String?,
                var isSelected: Boolean = false
            )
        }
    }
}