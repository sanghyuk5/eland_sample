package com.pionnet.eland.model.tabmenu

import com.google.gson.annotations.SerializedName

data class TabData (
    @SerializedName("code") val code: String,
    @SerializedName("data") val tabInfo: TabInfo
) {
    data class TabInfo(
        @SerializedName("header_icon_list") var header_icon_list: List<HeaderIcon>?,
    ) {
      data class HeaderIcon(
        @SerializedName("webview") var webview: String?,
        @SerializedName("menu_cd") var menu_cd: String?,
        @SerializedName("api_url") var api_url: String?,
        @SerializedName("link_url") var link_url: String?,
        @SerializedName("menu_lank") var menu_lank: String?,
        @SerializedName("menu_nm") var menu_nm: String?,
        @SerializedName("menu_subtitle") var menu_subtitle: String,
      )
    }
}