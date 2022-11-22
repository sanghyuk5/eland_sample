package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class HomeData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        // 최상단 플리퍼 배너
        @SerializedName("home_mainbanner_list") var mainBanner: List<Banner>? = null,
        // 최상단 플리퍼 배너
        @SerializedName("home_categoryIcon_list") var categoryIcon: List<CategoryIcon>? = null,
        // 상단 라인 배너
        @SerializedName("home_banner_top_list") var multiBanner: List<Banner>? = null,
        // 타임 세일
        @SerializedName("home_timesale") var timeSale: Goods? = null,
        // 브랜드
        @SerializedName("home_brand_list") var brand: List<Brand>? = null,
        // 럭키딜
        @SerializedName("home_lucky_deal") var luckyDeal: LuckyDeal? = null,
        // 시즌 기획전
        @SerializedName("home_offers") var seasonPlan: SeasonPlan? = null,
        // 스토어 쇼핑
        @SerializedName("home_offline_shop") var storeShop: StoreShop? = null,
        // MD 추천
        @SerializedName("home_md") var mdRecommend: MDRecommend? = null,



        // 오프라이스
        @SerializedName("home_oprice_list") var oprice: List<OpriceList>? = null,
        // Kim's 장보기
        @SerializedName("home_today_market") var todayMarket: TodayMarket?
    ) {
        data class CategoryIcon(
            @SerializedName("image_url") var imageUrl: String?,
            @SerializedName("link_url") var linkUrl: String?,
            @SerializedName("title") var title: String?
        )

        data class Brand(
            @SerializedName("brand_name") var brand: String?,
            @SerializedName("image_url") var imageUrl: String?,
            @SerializedName("link_url") var linkUrl: String?
        )

        data class LuckyDeal(
            @SerializedName("goods_list") var goodsList: List<Goods>? = null,
            @SerializedName("subtitle") var subtitle: String?,
            @SerializedName("title") var title: String?,
        )

        data class SeasonPlan(
            @SerializedName("subtitle") var subtitle: String?,
            @SerializedName("title") var title: String?,
            @SerializedName("home_offers_list") var offerList: List<HomeOffer>? = null
        ) {
            data class HomeOffer(
                @SerializedName("image_url") var imageUrl: String?,
                @SerializedName("goods_list") var goodsList: List<Goods>? = null,
                @SerializedName("link_url") var linkUrl: String?
            )
        }

        data class StoreShop(
            @SerializedName("home_offline_shop_banner") var bannerList: List<Banner>? = null,
            @SerializedName("home_offline_shop_list") var goodsList: List<Goods>? = null,
            @SerializedName("subtitle") var subtitle: String?,
            @SerializedName("title") var title: String?
        )

        data class MDRecommend(
            @SerializedName("home_md_cat_list") var categoryList: List<CategoryList>? = null,
            @SerializedName("subtitle") var subtitle: String?,
            @SerializedName("title") var title: String?,
        ) {
            data class CategoryList(
                @SerializedName("home_md_goods") var goodsList: List<Goods>? = null,
                @SerializedName("image_url") var imageUrl: String?,
                @SerializedName("menu_title") var title: String?,
                var isSelected: Boolean = false
            )
        }







        data class OpriceList(
            @field:SerializedName("goods_list") var goods_list: List<Goods> = emptyList(),
            @field:SerializedName("title") var title: String?
        ) {
            constructor() : this(mutableListOf<Goods>(), "")
            data class Goods(
                @field:SerializedName("brand_nm") var brand_nm: String?,
                @field:SerializedName("cart_grp_cd") var cart_grp_cd: String?,
                @field:SerializedName("conts_dist_no") var conts_dist_no: String?,
                @field:SerializedName("conts_divi_cd") var conts_divi_cd: String?,
                @field:SerializedName("cust_sale_price") var cust_sale_price: Int?,
                @field:SerializedName("disp_ctg_no") var disp_ctg_no: String?,
                @field:SerializedName("field_recev_poss_yn") var field_recev_poss_yn: String?,
                @field:SerializedName("flag_img_path") var flag_img_path: String?,
                @field:SerializedName("flag_yn") var flag_yn: String?,
                @field:SerializedName("ga_action") var ga_action: String?,
                @field:SerializedName("ga_category") var ga_category: String?,
                @field:SerializedName("ga_label") var ga_label: String?,
                @field:SerializedName("goods_nm") var goods_nm: String?,
                @field:SerializedName("goods_no") var goods_no: String?,
                @field:SerializedName("image_url") var image_url: String,
                @field:SerializedName("link_url") var link_url: String?,
                @field:SerializedName("lot_deli_yn") var lot_deli_yn: String?,
                @field:SerializedName("market_price") var market_price: Int?,
                @field:SerializedName("no_image_url") var no_image_url: String?,
                @field:SerializedName("quick_deli_poss_yn") var quick_deli_poss_yn: String?,
                @field:SerializedName("rel_divi_cd") var rel_divi_cd: String?,
                @field:SerializedName("rel_no") var rel_no: String?,
                @field:SerializedName("sale_area_no") var sale_area_no: String?,
                @field:SerializedName("sale_price") var sale_price: Int?,
                @field:SerializedName("sale_rate") var sale_rate: Int?,
                @field:SerializedName("sale_shop_divi_cd") var sale_shop_divi_cd: String?,
                @field:SerializedName("tr_yn") var tr_yn: String?,
                @field:SerializedName("vir_vend_no") var vir_vend_no: String?
            )
        }

        data class TodayMarket(
            @field:SerializedName("home_today_list") var home_today_list: List<TodayItem> = emptyList(),
            @field:SerializedName("subtitle") var subtitle: String?,
            @field:SerializedName("title") var title: String?
        ) {
            data class TodayItem(
                @field:SerializedName("brand_nm") var brand_nm: String?,
                @field:SerializedName("cart_grp_cd") var cart_grp_cd: String?,
                @field:SerializedName("conts_dist_no") var conts_dist_no: String?,
                @field:SerializedName("conts_divi_cd") var conts_divi_cd: String?,
                @field:SerializedName("cust_sale_price") var cust_sale_price: Int?,
                @field:SerializedName("disp_ctg_no") var disp_ctg_no: String?,
                @field:SerializedName("field_recev_poss_yn") var field_recev_poss_yn: String?,
                @field:SerializedName("flag_img_path") var flag_img_path: String?,
                @field:SerializedName("flag_yn") var flag_yn: String?,
                @field:SerializedName("ga_action") var ga_action: String?,
                @field:SerializedName("ga_category") var ga_category: String?,
                @field:SerializedName("ga_label") var ga_label: String?,
                @field:SerializedName("goods_nm") var goods_nm: String?,
                @field:SerializedName("goods_no") var goods_no: String?,
                @field:SerializedName("image_url") var image_url: String?,
                @field:SerializedName("link_url") var link_url: String?,
                @field:SerializedName("market_price") var market_price: Int?,
                @field:SerializedName("no_image_url") var no_image_url: String?,
                @field:SerializedName("quick_deli_poss_yn") var quick_deli_poss_yn: String?,
                @field:SerializedName("rel_divi_cd") var rel_divi_cd: String?,
                @field:SerializedName("rel_no") var rel_no: String?,
                @field:SerializedName("sale_area_no") var sale_area_no: String?,
                @field:SerializedName("sale_price") var sale_price: Int?,
                @field:SerializedName("sale_rate") var sale_rate: Int?,
                @field:SerializedName("sale_shop_divi_cd") var sale_shop_divi_cd: String?,
                @field:SerializedName("tr_yn") var tr_yn: String?
            )
        }
    }
}
