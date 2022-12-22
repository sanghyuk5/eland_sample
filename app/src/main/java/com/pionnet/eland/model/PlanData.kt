package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class PlanData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("ctg_list") var categoryList: List<CategoryList>? = null,
        @SerializedName("paging_info") var pageInfo: PageInfo? = null,
        @SerializedName("plan_list") var planList: List<PlanList>? = null,
    ) {
        data class CategoryList(
            @SerializedName("img_path") var img_path: String?,
            @SerializedName("disp_ctg_no") var disp_ctg_no: String?,
            @SerializedName("ldisp_ctg_show_nm") var ldisp_ctg_show_nm: String?
        )

        data class PageInfo(
            @SerializedName("page_idx") var pageIndex: Int?,
            @SerializedName("rows_per_page") var page: String?,
            @SerializedName("total_count") var total: String?
        )

        data class PlanList(
            @SerializedName("goods_list") var goods: List<Goods>?,
            @SerializedName("image_url") var imageUrl: String?,
            @SerializedName("link_url") var linkUrl: String?
        )
    }
}