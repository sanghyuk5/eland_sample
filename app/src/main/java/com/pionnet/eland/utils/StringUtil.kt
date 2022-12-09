package com.pionnet.eland.utils

import android.provider.DocumentsContract
import android.webkit.URLUtil
import com.pionnet.eland.localData.DataManager
import org.jsoup.Jsoup

inline val String?.withBaseUrl: String
    get() = (if (this.isNullOrBlank())  ""
    else {
        if (URLUtil.isNetworkUrl(this)) this
        else DataManager.webBaseUrl + this
    })

fun changedHeaderHtml(htmlText: String): String {
    val head = "<html><head>" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />" +
            "<style>body {margin: 0px;padding: 0px;}</style> " +
            "</head><body>"
    val closedTag = "</body></html>"
    var changeFontHtml = head + htmlText + closedTag
    val doc = Jsoup.parse(changeFontHtml)

    changeFontHtml = doc.toString()
    return changeFontHtml
}