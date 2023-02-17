package com.pionnet.eland.utils

import android.net.Uri
import android.webkit.URLUtil
import com.pionnet.eland.data.DataManager
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

//    val hyperLink = doc.getElementsByTag("a")
//    if (hyperLink.size > 0) {
//        for (i in 0 until hyperLink.size) {
//            var href: String = hyperLink.get(i).attr("href")
//            if (href.isNotEmpty() && !href.startsWith("http")) {
//                href = DataManager.DEFAULT_SERVER + href
//                hyperLink[i].attr("href", href)
//            }
//        }
//    }

    changeFontHtml = doc.toString()
    return changeFontHtml
}

inline val String.isGoodsDetailUrl: Boolean
    get() = Uri.parse(this).lastPathSegment.equals("initGoodsDetail.action", true)
