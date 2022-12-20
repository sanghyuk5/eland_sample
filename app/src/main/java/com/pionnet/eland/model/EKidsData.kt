package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class EKidsData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("main_banner") var mainBanner: List<Banner>? = null,
        @SerializedName("ctg_list") var category: List<Category>? = null,
        @SerializedName("band_banner") var bandBanner: List<Banner>? = null,
        @SerializedName("sub_banner") var subBanner: List<Banner>? = null,
        @SerializedName("special_deal") var specialDeal: SpecialDeal? = null,
        @SerializedName("brand_story") var brandStory: BrandStory? = null,
        @SerializedName("weekly_best") var weeklyBest: ExpandGroup? = null,
        @SerializedName("new_arrival") var newArrival: ExpandGroup? = null
    ) {
        data class Category(
            @SerializedName("img_path") var imagePath: String? = null,
            @SerializedName("link_url") var linkUrl: String? = null,
            @SerializedName("title") var name: String? = null
        )

        data class SpecialDeal(
            @SerializedName("goods_list") var goods: List<Goods>? = null,
            @SerializedName("title") var title: String? = null
        )

        data class ExpandGroup(
            @SerializedName("title") var title: String? = null,
            @SerializedName("group") var group: List<Group>? = null
        ) {
            data class Group(
                @SerializedName("goods_list") var goods: List<Goods>? = null,
                @SerializedName("ctg_nm") var name: String? = null,
                var isSelected: Boolean
            )
        }

        data class BrandStory(
            @SerializedName("banner_list") var banner: List<Banner>? = null,
            @SerializedName("title") var title: String? = null
        )
    }
}