package com.pionnet.eland.localData

object DataManager {

    // 앱 글로벌 데이타
    var isAppRunning = false

    var isIntroToMain = false

    /**
     * common url
     */
    const val DEFAULT_SERVER = "https://m.elandmall.com" // todo: 상황에 따라 변경!!

    var webBaseUrl = DEFAULT_SERVER

    /**
     * data
     */
    var mbrVendNo = ""

    /**
     * intent parameter
     */
    const val EXTRA_LINK = "extra_link"
}