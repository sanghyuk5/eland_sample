package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class SearchPlanShopData(
    @SerializedName("result")
    val result: List<Result>?
) {
    data class Result(
        @SerializedName("planshop") val planshop: List<Planshop>?
    ) {
        data class Planshop(
            @SerializedName("banner_img_path") val bannerImgPath: String?,
            @SerializedName("disp_ctg_nm") val dispCtgNm: String?,
            @SerializedName("disp_ctg_no") val dispCtgNo: String?
        )
    }
}