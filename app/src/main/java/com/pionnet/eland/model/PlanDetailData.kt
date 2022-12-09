package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class PlanDetailData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        @SerializedName("goods_info") var goodsInfo: List<GoodsInfo>? = null,
        @SerializedName("tab_list") var tabList: List<TabList>? = null,
        @SerializedName("plan_shop_info") var shopInfo: ShopInfo? = null
    ) {
        data class GoodsInfo(
            @SerializedName("goods_list") var goodsList: List<Goods>? = null,
            @SerializedName("disp_ctg_nm") var name: String? = null
        )

        data class TabList(
            @SerializedName("disp_ctg_nm") var name: String? = null
        )

        data class ShopInfo(
            @SerializedName("banner_info") var bannerInfo: BannerInfo? = null,
            @SerializedName("share_img_path") var shareImagePath: String? = null,
            @SerializedName("plan_shop_nm") var name: String? = null,
            @SerializedName("share_url") var shareUrl: String? = null
        ) {
            data class BannerInfo(
                @SerializedName("html_cont") var html: String? = null,
                @SerializedName("link_url") var linkUrl: String? = null
            )
        }
    }
}