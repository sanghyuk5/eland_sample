package com.pionnet.eland.model.lucky

import com.google.gson.annotations.SerializedName


data class LuckyData (
    @field:SerializedName("code") val code: String,
    @field:SerializedName("data") val data: Data,
    @field:SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @field:SerializedName("conr_set_cmps_no") var conr_set_cmps_no: String,
        @field:SerializedName("conr_set_no") var conr_set_no: String,
        @field:SerializedName("disp_ctg_list") var disp_ctg_list: List<DispCtgList> = emptyList(),
        @field:SerializedName("new_goods_list") var new_goods_list: List<NewGoodList> = emptyList()

    ) {
        data class DispCtgList(
            @field:SerializedName("conr_set_cmps_no") var conr_set_cmps_no: String,
            @field:SerializedName("conr_set_no") var conr_set_no: String,
            @field:SerializedName("ctg_nm") var ctg_nm: String,
            @field:SerializedName("ga_action") var ga_action: String,
            @field:SerializedName("ga_category") var ga_category: String,
            @field:SerializedName("ga_label") var ga_label: String,
            @field:SerializedName("img") var img: String?
        )
        data class NewGoodList(
            @field:SerializedName("brand_nm") var brand_nm: String,
            @field:SerializedName("cart_grp_cd") var cart_grp_cd: String,
            @field:SerializedName("conts_dist_no") var conts_dist_no: String,
            @field:SerializedName("conts_divi_cd") var conts_divi_cd: String,
            @field:SerializedName("cust_sale_price") var cust_sale_price: Int,
            @field:SerializedName("disp_ctg_no") var disp_ctg_no: String,
            @field:SerializedName("field_recev_poss_yn") var field_recev_poss_yn: String,
            @field:SerializedName("flag_img_path") var flag_img_path: String,
            @field:SerializedName("flag_yn") var flag_yn: String,
            @field:SerializedName("ga_action") var ga_action: String,
            @field:SerializedName("ga_category") var ga_category: String,
            @field:SerializedName("ga_label") var ga_label: String,
            @field:SerializedName("goods_nm") var goods_nm: String,
            @field:SerializedName("goods_no") var goods_no: String,
            @field:SerializedName("icon_view") var icon_view: String,
            @field:SerializedName("image_url") var image_url: String,
            @field:SerializedName("link_url") var link_url: String,
            @field:SerializedName("market_price") var market_price: Int,
            @field:SerializedName("no_image_url") var no_image_url: String,
            @field:SerializedName("quick_deli_poss_yn") var quick_deli_poss_yn: String,
            @field:SerializedName("rel_divi_cd") var rel_divi_cd: String,
            @field:SerializedName("rel_no") var rel_no: String,
            @field:SerializedName("sale_area_no") var sale_area_no: String,
            @field:SerializedName("sale_price") var sale_price: Int,
            @field:SerializedName("sale_qty") var sale_qty: Int,
            @field:SerializedName("sale_rate") var sale_rate: Int,
            @field:SerializedName("sale_shop_divi_cd") var sale_shop_divi_cd: String,
            @field:SerializedName("tr_yn") var tr_yn: String,
            @field:SerializedName("vir_vend_no") var vir_vend_no: String,
            @field:SerializedName("wish_cart_Yn") var wish_cart_Yn: String,
            @field:SerializedName("wish_zzim_Yn") var wish_zzim_Yn: String,
        )
    }
}