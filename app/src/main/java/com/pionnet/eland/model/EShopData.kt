package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class EShopData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("eshop_main_promotion_list") var mainBanner: List<Banner>? = null,
        @SerializedName("eshop_sub_banner_list") var subBanner: List<Banner>? = null,
        @SerializedName("eshop_now_issue") var issue: Issue? = null,
        @SerializedName("eshop_banner_list1") var banner1: List<Banner>? = null,
        @SerializedName("eshop_banner_list2") var banner2: List<Banner>? = null,
        @SerializedName("eshop_now_review") var review: List<Review>? = null,
        @SerializedName("eshop_timesale") var timeSale: List<TimeSale>? = null,
        @SerializedName("eshop_now_arrival") var arrival: Arrival? = null
    ) {
        data class Issue(
            @SerializedName("title") var title: String? = null,
            @SerializedName("group") var group: List<Group>? = null
        )

        data class Review(
            @SerializedName("title") var title: String? = null,
            @SerializedName("goods_eval_list") var goods: List<Goods>? = null
        )

        data class TimeSale(
            @SerializedName("title") var title: String? = null,
            @SerializedName("goods_list") var goods: List<Goods>? = null
        )

        data class Arrival(
            @SerializedName("title") var title: String? = null,
            @SerializedName("group") var group: List<Group>? = null
        )

        data class Group(
            @SerializedName("goods_list") var goods: List<Goods>? = null,
            @SerializedName("tab_title") var title: String? = null,
            @SerializedName("planshop_list") var banner: Banner? = null,
            @SerializedName("category") var category: String? = null,
            var isSelected: Boolean = false
        )
    }
}