package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class BestData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("disp_ctg_list") val categoryList: List<CategoryList>? = null,
        @SerializedName("goods_info") val goodsInfo: GoodsInfo? = null,
    ) {
        data class CategoryList(
            @SerializedName("img_path") val image: String?,
            @SerializedName("disp_ctg_show_nm") val disp_ctg_show_nm: String?,
            @SerializedName("disp_ctg_no") val disp_ctg_no: String?,
            @SerializedName("ldisp_ctg_show_nm") val name: String?,
            @SerializedName("ldisp_ctg_no") val ldisp_ctg_no: String?
        )

        data class GoodsInfo(
            @SerializedName("goods_list") val goods: List<Goods>? = null,
        )
    }
}