package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName
import com.pionnet.eland.model.GoodsDetailData.Data.GoodsReviewInfo
import com.pionnet.eland.model.GoodsDetailData.Data.GoodsQuestionInfo

data class Banner(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("link_url") val linkUrl: String?,
    @SerializedName("rel_cont_nm") val name: String?
)

data class Goods(
    @SerializedName("brand_nm") val brandName: String?,
    @SerializedName("cust_sale_price") val salePrice: Int?,
    @SerializedName("flag_img_path") val flagImage: String?,
    @SerializedName("goods_nm") val goodsName: String?,
    @SerializedName("icon_view") val iconView: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("link_url") val linkUrl: String?,
    @SerializedName("market_price") val marketPrice: Int?,
    @SerializedName("sale_qty") val saleQty: Int?,
    @SerializedName("sale_rate") val saleRate: Int?,
    @SerializedName("goods_star_point") val starPoint: Int?,
    @SerializedName("goods_comment_count") val commentCnt: Int?,
    @SerializedName("favorite_yn") val favoriteYn: String?,

    // home tab > time sale
    @SerializedName("time") val time: String?,
    @SerializedName("title") val title: String?,

    // goods detail
    @SerializedName("brand_image_url") val brandImageUrl: String?,
    @SerializedName("final_price") val finalPrice: Int?,
    @SerializedName("coupon_sale_price") val couponSalePrice: Int?,
    @SerializedName("point") val point: String?,
    @SerializedName("goods_review_info") val goodsReviewInfo: GoodsReviewInfo?,
    @SerializedName("goods_question_info") val goodsQuestionInfo: GoodsQuestionInfo?
)

data class Category(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("link_url") val linkUrl: String? = null,
    @SerializedName("title") val title: String?,
    var isSelected: Boolean = false
)

data class ViewTypeDataSet(
    val viewType: ViewType,
    var data: Any? = null
)

enum class ViewType {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    SEVENTH,
    EIGHTH
}