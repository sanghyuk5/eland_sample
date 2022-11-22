package com.pionnet.eland.model.best

import com.google.gson.annotations.SerializedName

data class BestCtgData(
    @field:SerializedName("code") val code: String,
    @field:SerializedName("data") val data: Data,
    @field:SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @field:SerializedName("goods_info") var goods_info: GoodsInfo
    ) {
        data class GoodsInfo(
            @field:SerializedName("page_idx") val page_idx: String,
            @field:SerializedName("goods_list") val goods_list: List<GoodsList> = emptyList(),
            @field:SerializedName("rows_per_page") val rows_per_page: String,
            @field:SerializedName("goods_Total_count") val goods_Total_count: Int
        ) {
            data class GoodsList(
                @field:SerializedName("brand_nm") val brand_nm: String,
                @field:SerializedName("cart_grp_cd") val cart_grp_cd: String,
                @field:SerializedName("conts_dist_no") val conts_dist_no: String,
                @field:SerializedName("conts_divi_cd") val conts_divi_cd: String,
                @field:SerializedName("cust_sale_price") val cust_sale_price: Int,

                @field:SerializedName("disp_ctg_no") val disp_ctg_no: String,
                @field:SerializedName("field_recev_poss_yn") val field_recev_poss_yn: String,
                @field:SerializedName("flag_img_path") val flag_img_path: String,
                @field:SerializedName("flag_yn") val flag_yn: String,
                @field:SerializedName("ga_action") val ga_action: String,

                @field:SerializedName("ga_category") val ga_category: String,
                @field:SerializedName("ga_label") val ga_label: String,
                @field:SerializedName("goods_nm") val goods_nm: String,
                @field:SerializedName("goods_no") val goods_no: String,
                @field:SerializedName("icon_view") val icon_view: String,

                @field:SerializedName("image_url") val image_url: String,
                @field:SerializedName("link_url") val link_url: String,
                @field:SerializedName("market_price") val market_price: Int,
                @field:SerializedName("no_image_url") val no_image_url: String,
                @field:SerializedName("quick_deli_poss_yn") val quick_deli_poss_yn: String,

                @field:SerializedName("rel_divi_cd") val rel_divi_cd: String,
                @field:SerializedName("rel_no") val rel_no: String,
                @field:SerializedName("sale_area_no") val sale_area_no: String,
                @field:SerializedName("sale_price") val sale_price: Int,
                @field:SerializedName("sale_qty") val sale_qty: Int,

                @field:SerializedName("sale_rate") val sale_rate: Int,
                @field:SerializedName("sale_shop_divi_cd") val sale_shop_divi_cd: String,
                @field:SerializedName("tr_yn") val tr_yn: String,
                @field:SerializedName("vir_vend_no") val vir_vend_no: String,
                @field:SerializedName("wish_cart_Yn") val wish_cart_Yn: String,
                @field:SerializedName("wish_zzim_Yn") val wish_zzim_Yn: String
            )
        }
    }
}
