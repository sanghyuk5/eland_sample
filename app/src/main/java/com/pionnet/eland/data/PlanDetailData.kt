package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class PlanDetailData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("goods_info") val goodsInfo: List<GoodsInfo>? = null,
        @SerializedName("tab_list") val tabList: List<TabList>? = null,
        @SerializedName("plan_shop_info") val shopInfo: ShopInfo? = null
    ) {
        data class GoodsInfo(
            @SerializedName("goods_list") val goodsList: List<Goods>? = null,
            @SerializedName("disp_ctg_nm") val name: String? = null
        )

        data class TabList(
            @SerializedName("disp_ctg_nm") val name: String? = null
        )

        data class ShopInfo(
            @SerializedName("banner_info") val bannerInfo: BannerInfo? = null,
            @SerializedName("share_img_path") val shareImagePath: String? = null,
            @SerializedName("plan_shop_nm") val name: String? = null,
            @SerializedName("share_url") val shareUrl: String? = null
        ) {
            data class BannerInfo(
                @SerializedName("html_cont") val html: String? = null,
                @SerializedName("link_url") val linkUrl: String? = null
            )
        }
    }
}