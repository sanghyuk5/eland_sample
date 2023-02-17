package com.pionnet.eland.data

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
    const val EXTRA_PUSH = "extra_push"
    const val EXTRA_PUSH_DATE = "extra_push_date"
    const val EXTRA_PUSH_TITLE = "extra_push_title"
    const val EXTRA_PUSH_CONTENT = "extra_push_content"
    const val EXTRA_PUSH_WEB_LINK = "extra_push_web_link"
    const val EXTRA_PUSH_IMAGE = "extra_push_image"
    const val EXTRA_DEEP_LINK = "extra_deep_link"

    /**
     * siv scheme/host
     */
    const val SCHEME = "https"
    const val HOST_DEEPLINK = "elandmalltest.page.link"


    const val ARG_LIST = "list"
}