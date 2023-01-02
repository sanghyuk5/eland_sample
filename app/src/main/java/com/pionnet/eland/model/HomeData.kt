package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class HomeData(
    @SerializedName("code") val code: String,
    @SerializedName("data") val data: Data,
    @SerializedName("resultMsg") val resultMsg: String
) {
    data class Data(
        // 최상단 플리퍼 배너
        @SerializedName("home_mainbanner_list") val mainBanner: List<Banner>? = null,
        // 최상단 플리퍼 배너
        @SerializedName("home_categoryIcon_list") val categoryIcon: List<CategoryIcon>? = null,
        // 상단 라인 배너
        @SerializedName("home_banner_top_list") val multiBanner: List<Banner>? = null,
        // 타임 세일
        @SerializedName("home_timesale") val timeSale: Goods? = null,
        // 브랜드
        @SerializedName("home_brand_list") val brand: List<Brand>? = null,
        // 럭키딜
        @SerializedName("home_lucky_deal") val luckyDeal: LuckyDeal? = null,
        // 시즌 기획전
        @SerializedName("home_offers") val seasonPlan: SeasonPlan? = null,
        // 스토어 쇼핑
        @SerializedName("home_offline_shop") val storeShop: StoreShop? = null,
        // MD 추천
        @SerializedName("home_md") val mdRecommend: MDRecommend? = null,



        // 오프라이스
        @SerializedName("home_oprice_list") val oprice: List<OpriceList>? = null,
        // Kim's 장보기
        @SerializedName("home_today_market") val todayMarket: TodayMarket?
    ) {
        data class CategoryIcon(
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("title") val title: String?
        )

        data class Brand(
            @SerializedName("brand_name") val brand: String?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?
        )

        data class LuckyDeal(
            @SerializedName("goods_list") val goodsList: List<Goods>? = null,
            @SerializedName("subtitle") val subtitle: String?,
            @SerializedName("title") val title: String?,
        )

        data class SeasonPlan(
            @SerializedName("subtitle") val subtitle: String?,
            @SerializedName("title") val title: String?,
            @SerializedName("home_offers_list") val offerList: List<HomeOffer>? = null
        ) {
            data class HomeOffer(
                @SerializedName("image_url") val imageUrl: String?,
                @SerializedName("goods_list") val goodsList: List<Goods>? = null,
                @SerializedName("link_url") val linkUrl: String?
            )
        }

        data class StoreShop(
            @SerializedName("home_offline_shop_banner") val bannerList: List<Banner>? = null,
            @SerializedName("home_offline_shop_list") val goodsList: List<Goods>? = null,
            @SerializedName("subtitle") val subtitle: String?,
            @SerializedName("title") val title: String?
        )

        data class MDRecommend(
            @SerializedName("home_md_cat_list") val categoryList: List<CategoryList>? = null,
            @SerializedName("subtitle") val subtitle: String?,
            @SerializedName("title") val title: String?,
        ) {
            data class CategoryList(
                @SerializedName("home_md_goods") val goodsList: List<Goods>? = null,
                @SerializedName("image_url") val imageUrl: String?,
                @SerializedName("menu_title") val title: String?
            )
        }







        data class OpriceList(
            @field:SerializedName("goods_list") val goods_list: List<Goods> = emptyList(),
            @field:SerializedName("title") val title: String?
        ) {
            constructor() : this(mutableListOf<Goods>(), "")
            data class Goods(
                @field:SerializedName("brand_nm") val brand_nm: String?,
                @field:SerializedName("cart_grp_cd") val cart_grp_cd: String?,
                @field:SerializedName("conts_dist_no") val conts_dist_no: String?,
                @field:SerializedName("conts_divi_cd") val conts_divi_cd: String?,
                @field:SerializedName("cust_sale_price") val cust_sale_price: Int?,
                @field:SerializedName("disp_ctg_no") val disp_ctg_no: String?,
                @field:SerializedName("field_recev_poss_yn") val field_recev_poss_yn: String?,
                @field:SerializedName("flag_img_path") val flag_img_path: String?,
                @field:SerializedName("flag_yn") val flag_yn: String?,
                @field:SerializedName("ga_action") val ga_action: String?,
                @field:SerializedName("ga_category") val ga_category: String?,
                @field:SerializedName("ga_label") val ga_label: String?,
                @field:SerializedName("goods_nm") val goods_nm: String?,
                @field:SerializedName("goods_no") val goods_no: String?,
                @field:SerializedName("image_url") val image_url: String,
                @field:SerializedName("link_url") val link_url: String?,
                @field:SerializedName("lot_deli_yn") val lot_deli_yn: String?,
                @field:SerializedName("market_price") val market_price: Int?,
                @field:SerializedName("no_image_url") val no_image_url: String?,
                @field:SerializedName("quick_deli_poss_yn") val quick_deli_poss_yn: String?,
                @field:SerializedName("rel_divi_cd") val rel_divi_cd: String?,
                @field:SerializedName("rel_no") val rel_no: String?,
                @field:SerializedName("sale_area_no") val sale_area_no: String?,
                @field:SerializedName("sale_price") val sale_price: Int?,
                @field:SerializedName("sale_rate") val sale_rate: Int?,
                @field:SerializedName("sale_shop_divi_cd") val sale_shop_divi_cd: String?,
                @field:SerializedName("tr_yn") val tr_yn: String?,
                @field:SerializedName("vir_vend_no") val vir_vend_no: String?
            )
        }

        data class TodayMarket(
            @field:SerializedName("home_today_list") val home_today_list: List<TodayItem> = emptyList(),
            @field:SerializedName("subtitle") val subtitle: String?,
            @field:SerializedName("title") val title: String?
        ) {
            data class TodayItem(
                @field:SerializedName("brand_nm") val brand_nm: String?,
                @field:SerializedName("cart_grp_cd") val cart_grp_cd: String?,
                @field:SerializedName("conts_dist_no") val conts_dist_no: String?,
                @field:SerializedName("conts_divi_cd") val conts_divi_cd: String?,
                @field:SerializedName("cust_sale_price") val cust_sale_price: Int?,
                @field:SerializedName("disp_ctg_no") val disp_ctg_no: String?,
                @field:SerializedName("field_recev_poss_yn") val field_recev_poss_yn: String?,
                @field:SerializedName("flag_img_path") val flag_img_path: String?,
                @field:SerializedName("flag_yn") val flag_yn: String?,
                @field:SerializedName("ga_action") val ga_action: String?,
                @field:SerializedName("ga_category") val ga_category: String?,
                @field:SerializedName("ga_label") val ga_label: String?,
                @field:SerializedName("goods_nm") val goods_nm: String?,
                @field:SerializedName("goods_no") val goods_no: String?,
                @field:SerializedName("image_url") val image_url: String?,
                @field:SerializedName("link_url") val link_url: String?,
                @field:SerializedName("market_price") val market_price: Int?,
                @field:SerializedName("no_image_url") val no_image_url: String?,
                @field:SerializedName("quick_deli_poss_yn") val quick_deli_poss_yn: String?,
                @field:SerializedName("rel_divi_cd") val rel_divi_cd: String?,
                @field:SerializedName("rel_no") val rel_no: String?,
                @field:SerializedName("sale_area_no") val sale_area_no: String?,
                @field:SerializedName("sale_price") val sale_price: Int?,
                @field:SerializedName("sale_rate") val sale_rate: Int?,
                @field:SerializedName("sale_shop_divi_cd") val sale_shop_divi_cd: String?,
                @field:SerializedName("tr_yn") val tr_yn: String?
            )
        }
    }
}
