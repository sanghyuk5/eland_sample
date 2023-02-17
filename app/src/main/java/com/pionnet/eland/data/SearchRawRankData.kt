package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class SearchRawRankData(
    @SerializedName("result")
    val result: List<List<String?>?>?
)

data class SearchRank(
    var rank: String? = null,
    var keyword: String? = null,
    var isTopFive: Boolean = false
)