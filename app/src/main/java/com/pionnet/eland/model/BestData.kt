package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class BestData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("disp_ctg_list") var categoryList: List<CategoryList>? = null,
        @SerializedName("goods_info") var goodsInfo: GoodsInfo? = null,
    ) {
        data class CategoryList(
            @SerializedName("img_path") var image: String?,
            @SerializedName("disp_ctg_show_nm") var disp_ctg_show_nm: String?,
            @SerializedName("disp_ctg_no") var disp_ctg_no: String?,
            @SerializedName("ldisp_ctg_show_nm") var name: String?,
            @SerializedName("ldisp_ctg_no") var ldisp_ctg_no: String?
        )

        data class GoodsInfo(
            @SerializedName("goods_list") var goods: List<Goods>? = null,
        )
    }
}