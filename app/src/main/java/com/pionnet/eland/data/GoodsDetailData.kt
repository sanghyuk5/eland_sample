package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class GoodsDetailData(
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("top_image_list") val topImageList: List<Banner?>?,
        @SerializedName("goods_info") val goodsInfo: Goods?,
        @SerializedName("share") val share: Share?,
        @SerializedName("seller_popular_style") val sellerPopularStyle: TitleGoods?,
        @SerializedName("goods_detail") val goodsDetail: String?,
        @SerializedName("seller_recommend_goods") val sellerRecommendGoods: List<Goods?>?,
        @SerializedName("tag_list") val tagList: List<Tag?>?,
        @SerializedName("seller_popular_goods") val sellerPopularGoods: List<Goods?>?,
        @SerializedName("recommend_goods") val recommendGoods: List<Goods?>?,
        @SerializedName("popular_goods") val popularGoods: TitleGoods?,
        @SerializedName("order_info") val orderInfo: OrderInfo?
    ) {
        data class GoodsReviewInfo(
            @SerializedName("review_count") val reviewCount: Int?,
            @SerializedName("review_info") val reviewInfo: ReviewInfo?
        ) {
            data class ReviewInfo(
                @SerializedName("review_image_info") val reviewImageInfo: ReviewImageTextInfo?,
                @SerializedName("review_text_info") val reviewTextInfo: ReviewImageTextInfo?,
            ) {
                data class ReviewImageTextInfo(
                    @SerializedName("review_count") val reviewCount: Int?,
                    @SerializedName("review_list") val reviewList: List<Review?>?,
                    @SerializedName("review_more_url") val reviewMoreUrl: String?
                ) {
                    data class Review(
                        @SerializedName("image_url") val imageUrl: String?,
                        @SerializedName("userID") val userID: String?,
                        @SerializedName("height") val height: String?,
                        @SerializedName("weight") val weight: String?,
                        @SerializedName("purchase_goods_info") val purchaseGoodsInfo: String?,
                        @SerializedName("review_comment") val reviewComment: String?,
                        @SerializedName("link_url") val linkUrl: String?
                    )
                }
            }
        }

        data class GoodsQuestionInfo(
            @SerializedName("question_count") val questionCount: Int?,
            @SerializedName("question_list") val questionList: List<Question?>?,
            @SerializedName("question_more_url") val moreUrl: String?,
        ) {
            data class Question(
                @SerializedName("userID") val userID: String?,
                @SerializedName("date") val date: String?,
                @SerializedName("content") val content: String?,
                @SerializedName("secret") val secret: Boolean?
            )
        }

        data class Share(
            @SerializedName("share_img_path") val shareImgPath: String?,
            @SerializedName("share_url") val shareUrl: String?
        )

        data class TitleGoods(
            @SerializedName("title") val title: String?,
            @SerializedName("goods_list") val goods: List<Goods>?
        )

        data class Tag(
            @SerializedName("tag_nm") val name: String?,
            @SerializedName("tag_link") val link: String?
        )

        data class OrderInfo(
            @SerializedName("bank_info") val bankInfo: String?,
            @SerializedName("account_info") val accountInfo: String?,
            @SerializedName("bank_owner") val bankOwner: String?,
            @SerializedName("address") val address: String?,
            @SerializedName("business_name") val businessName: String?,
            @SerializedName("representative") val representative: String?,
            @SerializedName("business_number") val businessNumber: String?,
            @SerializedName("mail_order_number") val mailOrderNumber: String?,
            @SerializedName("customer_center") val customerCenter: String?,
            @SerializedName("mail_address") val mailAddress: String?,
            @SerializedName("call_number") val callNumber: String?
        )

        data class OrderInfoText(
            var text: String?,
            var isTitle: Boolean = false
        )

        data class StoreInfo(
            var title: String?,
            var data: String?
        )
    }
}