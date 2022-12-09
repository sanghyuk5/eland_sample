package com.pionnet.eland.ui.webview

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import com.pionnet.eland.BuildConfig

open class ElandWebView : WebView {
    private var callback: Callback? = null
    private lateinit var elandWebViewClient: ElandWebViewClient
    private lateinit var elandWebChromeClient: ElandWebChromeClient

    interface Callback {
        fun onScrollChanged(dy: Int)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(context: Context) {
        if (isInEditMode) return

        setWebContentsDebuggingEnabled(BuildConfig.DEBUG)

        if (context is Callback) {
            setCallback(context)
        }

        settings.textZoom = 100
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.databaseEnabled = true
        settings.domStorageEnabled = true
        settings.saveFormData = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.setSupportMultipleWindows(true)
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(this, true)

        elandWebViewClient = ElandWebViewClient(context)
        webViewClient = elandWebViewClient

        elandWebChromeClient = ElandWebChromeClient(context)
        webChromeClient = elandWebChromeClient

    }

    private fun setCallback(callback: Callback) {
        this.callback = callback
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        callback?.onScrollChanged(t - oldt)
    }

    override fun loadUrl(url: String) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("javascript:")) {
                super.evaluateJavascript(url.replaceFirst("javascript:".toRegex(), ""), null)
            } else {
                super.loadUrl(url)
            }
        }
    }

    fun loadScript(script: String, callback: ValueCallback<String>) {
        super.evaluateJavascript(script, callback)
    }
}
