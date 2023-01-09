package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class LeftMenuDataSet(
    val viewType: LeftMenuViewType,
    var data: Any? = null
)

data class LeftMenuData(
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("login_info") val loginInfo: LoginInfo?,
        @SerializedName("nav_cat_top_menu_list") val topMenuList: List<TopMenu>?,
        @SerializedName("nav_cat_lately_goods_list") val recentlyList: List<Recently>?,
        @SerializedName("nav_cat_category_list") val categoryList: List<Category>?,
        @SerializedName("nav_cat_brand_list") val brandList: List<Brand>?,
        @SerializedName("nav_cat_shop_list") val shopList: List<Shop>?,
        @SerializedName("nav_cat_servicemenu_list") val serviceMenuList: List<ServiceMenu>?
    ) {
        data class LoginInfo(
            @SerializedName("autoLogin") val autoLogin: String?,
            @SerializedName("pop_type") val popType: String?,
            @SerializedName("birth_day") val birthDay: String?,
            @SerializedName("wedd_cele_day") val weddCeleDay: String?,
            @SerializedName("user_id") val userId: String?,
            @SerializedName("mbr_grade_nm") val mbrGradeNm: String?,
            @SerializedName("pop_confirm_url") val popConfirmUrl: String?,
            @SerializedName("pop_title") val popTitle: String?,
            @SerializedName("staff_yn") val staffYn: String?,
            @SerializedName("pop_url") val popUrl: String?,
            @SerializedName("mbr_nm") val mbrNm: String?,
            @SerializedName("cust_id") val custId: String?,
            @SerializedName("pop_cancel_url") val popCancelUrl: String?
        )

        data class TopMenu(
            @SerializedName("menu_info") val menuInfo: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("menu_nm") val name: String?,
            @SerializedName("cart_cnt") val cartCnt: Int?,
            @SerializedName("cupn_cnt") val cupnCnt: Int?
        )

        data class Recently(
            @SerializedName("img_path") val imgPath: String?
        )

        data class Category(
            @SerializedName("nav_cat_category_menu_list") val categoryMenu: List<CategoryMenu>?,
            @SerializedName("nav_cat_category_menu") val navCatCategoryMenu: String?
        ) {
            data class CategoryMenu(
                @SerializedName("image_url") val imageUrl: String?,
                @SerializedName("menu_title") val menuTitle: String?,
                @SerializedName("link_url") val linkUrl: String?
            ) {
                constructor() : this("", "", "")
            }
        }

        data class Brand(
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("brand_nm") val name: String?
        )

        data class Shop(
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("staff_yn") val staffYn: String?,
            @SerializedName("shop_nm") val name: String?
        )
        
        data class ServiceMenu(
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("service_menu") val serviceMenu: String?
        )
    }
}

enum class LeftMenuViewType {
    RECENT,
    DIVIDER,
    CATEGORY,
    BRAND,
    SHOP,
    SERVICE,
    BOTTOM
}