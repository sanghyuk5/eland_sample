package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class EKidsData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("main_banner") val mainBanner: List<Banner>? = null,
        @SerializedName("ctg_list") val category: List<Category>? = null,
        @SerializedName("band_banner") val bandBanner: List<Banner>? = null,
        @SerializedName("sub_banner") val subBanner: List<Banner>? = null,
        @SerializedName("special_deal") val specialDeal: SpecialDeal? = null,
        @SerializedName("brand_story") val brandStory: BrandStory? = null,
        @SerializedName("weekly_best") val weeklyBest: ExpandGroup? = null,
        @SerializedName("new_arrival") val newArrival: ExpandGroup? = null
    ) {
        data class Category(
            @SerializedName("img_path") val imagePath: String? = null,
            @SerializedName("link_url") val linkUrl: String? = null,
            @SerializedName("title") val name: String? = null
        )

        data class SpecialDeal(
            @SerializedName("goods_list") val goods: List<Goods>? = null,
            @SerializedName("title") val title: String? = null
        )

        data class ExpandGroup(
            @SerializedName("title") val title: String? = null,
            @SerializedName("group") val group: List<Group>? = null
        ) {
            data class Group(
                @SerializedName("goods_list") val goods: List<Goods>? = null,
                @SerializedName("ctg_nm") val name: String? = null,
                var isSelected: Boolean
            )
        }

        data class BrandStory(
            @SerializedName("banner_list") val banner: List<Banner>? = null,
            @SerializedName("title") val title: String? = null
        )
    }
}