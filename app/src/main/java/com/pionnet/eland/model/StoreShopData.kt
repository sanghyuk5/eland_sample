package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class StoreShopData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("store_mainbanner_list") var mainBanner: List<Banner>? = null,
        @SerializedName("banjjak_deli") var delivery: Delivery? = null,
        @SerializedName("recommend_store_list") var recommend: List<Recommend>? = null,
        @SerializedName("my_regular_store_list") var regular: List<Regular>? = null,
        @SerializedName("smartpick_list") var smartPick: List<SmartPick>? = null,
        @SerializedName("category_goods_list") var categoryGoods: List<CategoryGoods>? = null
    ) {
        data class Delivery(
            @SerializedName("btn_nm") var btnNm: String?,
            @SerializedName("flag") var flag: String?,
            @SerializedName("btn_url") var btnUrl: String?,
            @SerializedName("img_path") var imagePath: String?,
            @SerializedName("img_link") var imageLink: String?,
            @SerializedName("addr") var addr: String?
        )

        data class Recommend(
            @SerializedName("store_nm") var storeNm: String?,
            @SerializedName("image_url") var imageUrl: String?,
            @SerializedName("link_url") var linkUrl: String?
        )

        data class Regular(
            @SerializedName("low_vend_nm") var name: String?,
            @SerializedName("vend_no") var vendNo: String?,
            @SerializedName("vir_vend_no") var virVendNo: String?,
            @SerializedName("goods_list") var goodsList: List<Goods>?
        )

        data class SmartPick(
            @SerializedName("rel_cont_nm") var name: String?,
            @SerializedName("category_no") var categoryNo: String?
        )

        data class CategoryGoods(
            @SerializedName("goods_list") var goodsList: List<Goods>?,
            @SerializedName("ctg_no") var ctgNo: String?,
            @SerializedName("dactive_img_url") var deActiveImageUrl: String?,
            @SerializedName("ctg_nm") var ctgNm: String?,
            @SerializedName("active_img_url") var activeImageUrl: String?,
            var isSelected: Boolean = false
        )
    }
}
