package com.pionnet.eland

import android.os.Bundle
import com.orhanobut.logger.Logger
import com.pionnet.eland.BaseActivity
import com.pionnet.eland.localData.DataManager.EXTRA_LINK
import com.pionnet.eland.utils.debugToast

class WebViewActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            Logger.d("isSavedInstanceState is true, return")
            return
        }

        var url: String? = intent.getStringExtra(EXTRA_LINK)

        debugToast(this, "click data : $url")
    }
}