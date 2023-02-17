package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class TabData (
    @SerializedName("code") val code: String,
    @SerializedName("data") val tabInfo: TabInfo
) {
    data class TabInfo(
        @SerializedName("header_icon_list") val header_icon_list: List<HeaderIcon>?,
    ) {
      data class HeaderIcon(
        @SerializedName("webview") val webview: String?,
        @SerializedName("menu_cd") val menu_cd: String?,
        @SerializedName("api_url") val api_url: String?,
        @SerializedName("link_url") val link_url: String?,
        @SerializedName("menu_lank") val menu_lank: String?,
        @SerializedName("menu_nm") val menu_nm: String?,
        @SerializedName("menu_subtitle") val menu_subtitle: String
      )
    }
}