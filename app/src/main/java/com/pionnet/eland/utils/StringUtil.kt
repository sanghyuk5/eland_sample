package com.pionnet.eland.utils

import android.webkit.URLUtil
import com.pionnet.eland.localData.DataManager

inline val String?.withBaseUrl: String
    get() = (if (this.isNullOrBlank())  ""
    else {
        if (URLUtil.isNetworkUrl(this)) this
        else DataManager.webBaseUrl + this
    })