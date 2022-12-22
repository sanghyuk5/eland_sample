package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class LuckyDealData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("conr_set_cmps_no") var setCmpsNo: String?,
        @SerializedName("conr_set_no") var setNo: String?,
        @SerializedName("disp_ctg_list") var categoryList: List<CategoryList>? = null,
        @SerializedName("new_goods_list") var goodsList: List<Goods>? = null
    ) {
        data class CategoryList(
            @SerializedName("conr_set_cmps_no") var setCmpsNo: String?,
            @SerializedName("conr_set_no") var setNo: String?,
            @SerializedName("ctg_nm") var name: String?,
            @SerializedName("img") var image: String?
        )
    }
}