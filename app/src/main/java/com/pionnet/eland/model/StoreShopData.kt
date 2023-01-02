package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class StoreShopData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("store_mainbanner_list") val mainBanner: List<Banner>? = null,
        @SerializedName("banjjak_deli") val delivery: Delivery? = null,
        @SerializedName("recommend_store_list") val recommend: List<Recommend>? = null,
        @SerializedName("my_regular_store_list") val regular: List<Regular>? = null,
        @SerializedName("smartpick_list") val smartPick: List<SmartPick>? = null,
        @SerializedName("category_goods_list") val categoryGoods: List<CategoryGoods>? = null
    ) {
        data class Delivery(
            @SerializedName("btn_nm") val btnNm: String?,
            @SerializedName("flag") val flag: String?,
            @SerializedName("btn_url") val btnUrl: String?,
            @SerializedName("img_path") val imagePath: String?,
            @SerializedName("img_link") val imageLink: String?,
            @SerializedName("addr") val addr: String?
        )

        data class Recommend(
            @SerializedName("store_nm") val storeNm: String?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?
        )

        data class Regular(
            @SerializedName("low_vend_nm") val name: String?,
            @SerializedName("vend_no") val vendNo: String?,
            @SerializedName("vir_vend_no") val virVendNo: String? //이게 사용
        )

        data class SmartPick(
            @SerializedName("rel_cont_nm") val name: String?,
            @SerializedName("category_no") val categoryNo: String?,
            var isSelected: Boolean
        )

        data class CategoryGoods(
            @SerializedName("goods_list") val goodsList: List<Goods>?,
            @SerializedName("ctg_no") val ctgNo: String?,
            @SerializedName("dactive_img_url") val deActiveImageUrl: String?,
            @SerializedName("ctg_nm") val ctgNm: String?,
            @SerializedName("active_img_url") val activeImageUrl: String?,
            var isSelected: Boolean = false
        )
    }
}
