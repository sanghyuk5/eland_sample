package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("image_url") var imageUrl: String?,
    @SerializedName("link_url") var linkUrl: String?,
    @SerializedName("rel_cont_nm") var name: String?
)

data class Goods(
    @SerializedName("brand_nm") var brand: String?,
    @SerializedName("cust_sale_price") var salePrice: Int?,
    @SerializedName("flag_img_path") var flagImage: String?,
    @SerializedName("goods_nm") var goodsName: String?,
    @SerializedName("icon_view") var iconView: String?,
    @SerializedName("image_url") var imageUrl: String?,
    @SerializedName("link_url") var linkUrl: String?,
    @SerializedName("market_price") var marketPrice: Int?,
    @SerializedName("sale_qty") var saleQty: Int?,
    @SerializedName("sale_rate") var saleRate: Int?,
    @SerializedName("time") var time: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("goods_star_point") var starPoint: Int?,
    @SerializedName("goods_comment_count") var commentCnt: Int?,
)

data class Category(
    @SerializedName("image_url") var imageUrl: String?,
    @SerializedName("link_url") var linkUrl: String?,
    @SerializedName("title") var title: String?
)