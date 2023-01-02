package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("link_url") val linkUrl: String?,
    @SerializedName("rel_cont_nm") val name: String?
)

data class Goods(
    @SerializedName("brand_nm") val brand: String?,
    @SerializedName("cust_sale_price") val salePrice: Int?,
    @SerializedName("flag_img_path") val flagImage: String?,
    @SerializedName("goods_nm") val goodsName: String?,
    @SerializedName("icon_view") val iconView: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("link_url") val linkUrl: String?,
    @SerializedName("market_price") val marketPrice: Int?,
    @SerializedName("sale_qty") val saleQty: Int?,
    @SerializedName("sale_rate") val saleRate: Int?,
    @SerializedName("time") val time: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("goods_star_point") val starPoint: Int?,
    @SerializedName("goods_comment_count") val commentCnt: Int?,
)

data class Category(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("link_url") val linkUrl: String? = null,
    @SerializedName("title") val title: String?,
    var isSelected: Boolean = false
)