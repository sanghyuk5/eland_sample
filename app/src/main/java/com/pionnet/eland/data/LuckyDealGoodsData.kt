package com.pionnet.eland.data

import com.google.gson.annotations.SerializedName

data class LuckyDealGoodsData(
    @SerializedName("code") val code: String?,
    @SerializedName("data") val data: Data?,
    @SerializedName("resultMsg") val resultMsg: String?
) {
    data class Data(
        @SerializedName("goods_info") val goodsInfo: GoodsInfo? = null
    ) {
        data class GoodsInfo(
            @SerializedName("goods_list") val goods: List<Goods>? = null
        )
    }
}
