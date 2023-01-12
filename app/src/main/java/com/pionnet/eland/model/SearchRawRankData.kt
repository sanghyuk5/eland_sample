package com.pionnet.eland.model

import com.google.gson.annotations.SerializedName

data class SearchRawRankData(
    @SerializedName("result")
    val result: List<List<String?>?>?
)

data class SearchRank(
    var rank: String?,
    var keyword: String?,
    var isTopFive: Boolean = false
)