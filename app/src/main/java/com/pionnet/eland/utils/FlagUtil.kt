package com.pionnet.eland.utils

object FlagUtil {
    fun from(beneTag: String?): List<String> =
        if (beneTag.isNullOrEmpty()) {
            listOf()
        } else {
            beneTag.dropLast(1).trim().splitToSequence(',').toList()
        }
}