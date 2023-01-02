package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class EShopData (
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("eshop_main_promotion_list") val mainBanner: List<Banner>? = null,
        @SerializedName("eshop_sub_banner_list") val subBanner: List<Banner>? = null,
        @SerializedName("eshop_now_issue") val issue: Issue? = null,
        @SerializedName("eshop_banner_list1") val banner1: List<Banner>? = null,
        @SerializedName("eshop_banner_list2") val banner2: List<Banner>? = null,
        @SerializedName("eshop_now_review") val review: List<Review>? = null,
        @SerializedName("eshop_timesale") val timeSale: List<TimeSale>? = null,
        @SerializedName("eshop_now_arrival") val arrival: Arrival? = null
    ) {
        data class Issue(
            @SerializedName("title") val title: String? = null,
            @SerializedName("group") val group: List<Group>? = null
        )

        data class Review(
            @SerializedName("title") val title: String? = null,
            @SerializedName("goods_eval_list") val goods: List<Goods>? = null
        )

        data class TimeSale(
            @SerializedName("title") val title: String? = null,
            @SerializedName("goods_list") val goods: List<Goods>? = null
        )

        data class Arrival(
            @SerializedName("title") val title: String? = null,
            @SerializedName("group") val group: List<Group>? = null
        )

        data class Group(
            @SerializedName("goods_list") val goods: List<Goods>? = null,
            @SerializedName("tab_title") var title: String? = null,
            @SerializedName("planshop_list") val banner: Banner? = null,
            @SerializedName("category") val category: String? = null,
            var isSelected: Boolean = false
        )
    }
}