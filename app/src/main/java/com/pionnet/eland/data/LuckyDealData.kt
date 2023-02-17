package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class LuckyDealData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("conr_set_cmps_no") val setCmpsNo: String?,
        @SerializedName("conr_set_no") val setNo: String?,
        @SerializedName("disp_ctg_list") val categoryList: List<CategoryList>? = null,
        @SerializedName("new_goods_list") val goodsList: List<Goods>? = null
    ) {
        data class CategoryList(
            @SerializedName("conr_set_cmps_no") val setCmpsNo: String?,
            @SerializedName("conr_set_no") val setNo: String?,
            @SerializedName("ctg_nm") val name: String?,
            @SerializedName("img") val image: String?
        )
    }
}