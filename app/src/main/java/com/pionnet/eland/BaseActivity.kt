package com.pionnet.eland

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.pionnet.eland.localData.DataManager.EXTRA_LINK
import com.pionnet.eland.ui.leftmenu.LeftMenuActivity
import com.pionnet.eland.ui.search.SearchActivity
import com.pionnet.eland.ui.splash.SplashActivity
import com.pionnet.eland.utils.debugToast
import com.pionnet.eland.utils.dialogAlert
import com.pionnet.eland.utils.isNetworkAvailable
import com.pionnet.eland.utils.withBaseUrl

open class BaseActivity : AppCompatActivity() {

    private val resultNavToWeb = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        onResultNavToWeb(it.resultCode)
    }

    protected fun isSavedInstanceState(savedInstanceState: Bundle?): Boolean {
        return if (savedInstanceState != null) {
            Logger.d("savedInstanceState is NOT null.")
            val intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finishAffinity()
            true
        } else {
            false
        }
    }

    protected open fun onLinkEvent(linkEvent: LinkEvent) {
        when (linkEvent.type) {
            LinkEventType.LEFT_MENU -> navToLeftMenu()
            LinkEventType.SEARCH -> navToSearch()
            LinkEventType.DEFAULT -> navToDefault(linkEvent.url)
            LinkEventType.DIAL -> navToDial(linkEvent.url)
        }
    }

    protected open fun onResultNavToWeb(resultCode: Int) {
        Logger.d("super.onResultNavToWeb()")
    }

    protected fun handleSivScheme(url: String) {
        Logger.d("handleSivScheme: $url")
        val uri = Uri.parse(url)
//        when (uri.host?.lowercase()) {
//
//        }
    }

    private fun navToLeftMenu() {
        if (isNetworkAvailable(this)) {
            startActivity(Intent(this, LeftMenuActivity::class.java))
        } else {
            dialogAlert(this, getString(R.string.msg_network_error))
        }
    }

    private fun navToSearch() {
        if (isNetworkAvailable(this)) {
            startActivity(Intent(this, SearchActivity::class.java))
        } else {
            dialogAlert(this, getString(R.string.msg_network_error))
        }
    }

    private fun navToDefault(url: String?) {
        if (url.isNullOrEmpty()) {
            debugToast(this, "empty url")
            return
        }

//        val mainTabScheme = url.matchedMainTabScheme
//        if (!mainTabScheme.isNullOrBlank()) {
//            navToMainTab(mainTabScheme)
//            return
//        }
//
//        if (url.isSivScheme) {
//            handleSivScheme(url)
//            return
//        }

        navToWeb(url)
    }

    private fun navToWeb(url: String) {
        if (url.isNullOrBlank()) {
            debugToast(this, "empty url")
            return
        }

        if (isNetworkAvailable(this)) {
            val intent = Intent(this, WebViewActivity::class.java)
                .putExtra(EXTRA_LINK, url.withBaseUrl)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

            resultNavToWeb.launch(intent)
        } else {
            dialogAlert(this, getString(R.string.msg_network_error))
        }
    }

    private fun navToDial(url: String?) {
        if (url.isNullOrEmpty()) return
        try {
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("tel:$url")))
        } catch (ex: Exception) {
            // NOP
        }
    }
}