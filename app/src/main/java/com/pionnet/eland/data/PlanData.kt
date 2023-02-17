package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class PlanData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("ctg_list") val categoryList: List<CategoryList>? = null,
        @SerializedName("paging_info") val pageInfo: PageInfo? = null,
        @SerializedName("plan_list") val planList: List<PlanList>? = null,
    ) {
        data class CategoryList(
            @SerializedName("img_path") val image: String?,
            @SerializedName("disp_ctg_no") val disp_ctg_no: String?,
            @SerializedName("disp_ctg_nm") val name: String?
        )

        data class PageInfo(
            @SerializedName("page_idx") val pageIndex: Int?,
            @SerializedName("rows_per_page") val page: String?,
            @SerializedName("total_count") val total: String?
        )

        data class PlanList(
            @SerializedName("goods_list") val goods: List<Goods>?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?
        )
    }
}