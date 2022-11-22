package com.pionnet.eland.utils

import java.text.DecimalFormat
import java.text.NumberFormat

fun priceLuckyDeal(value: Int): String {
    if (value < 0) return ""
    return if (value > 10000) {
        val tmp: Int = (value / 10000)
        tmp.toString() + "만+개 판매"
    } else {
        val formatter: NumberFormat = DecimalFormat("###,###")
        formatter.format(value) + "개 판매"
    }
}

fun priceFormat(value: Int): String? {
    if (value < 0) return ""
    val formatter: NumberFormat = DecimalFormat("###,###")
    return formatter.format(value)
}
