package com.pionnet.eland.ui.webview

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Message
import android.view.View
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import com.orhanobut.logger.Logger

class ElandWebChromeClient(context: Context?) : WebChromeClient() {
    private var callback: Callback? = null

    interface Callback {
        fun onProgressChanged(progress: Int) {
            /* default NOP */
        }
        fun onReceivedTitle(title: String?) {
            /* default NOP */
        }
        fun onFileChooser(filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: FileChooserParams) {
            /* default NOP */
        }
        fun onExternalBrowser(url: String) {
            /* default NOP */
        }

        fun onShowCustomView(view: View, callback: CustomViewCallback) {
            /* default NOP */
        }

        fun onHideCustomView() {
            /* default NOP */
        }
    }

    init {
        if (context is Callback) {
            setCallback(context)
        }
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        callback?.onProgressChanged(newProgress)
    }

    override fun onReceivedTitle(view: WebView, title: String) {
        super.onReceivedTitle(view, title)
        callback?.onReceivedTitle(title)
    }

    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        callback?.onFileChooser(filePathCallback, fileChooserParams)
        return true
    }

    override fun onCreateWindow(
        view: WebView,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message
    ): Boolean {
        Logger.d("onCreateWindow: " + view.originalUrl)
        val newWebView = WebView(view.context)
        val transport = resultMsg.obj as WebViewTransport
        transport.webView = newWebView
        resultMsg.sendToTarget()
        newWebView.webViewClient = object : WebViewClient() {
            @SuppressLint("NewApi")
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return shouldOverrideUrlLoading(view, request.url.toString())
            }

            override fun shouldOverrideUrlLoading(View: WebView, url: String): Boolean {
                if (url.isNotEmpty()) {
//                    if (url.isNetworkUrl()) {
//                        callback?.onExternalBrowser(url)
//                        return true
//                    }
//
//                    val uri = Uri.parse(url)
//                    if (HOST_EXTERNAL_BROWSER.equals(uri.host, true)) {
//                        uri.getQueryParameter("link")?.let {
//                            callback?.onExternalBrowser(it)
//                        }
//                        return true
//                    }
                }
                return true
            }
        }
        return true
    }

    override fun onShowCustomView(view: View, callback: CustomViewCallback) {
        super.onShowCustomView(view, callback)

        this.callback?.onShowCustomView(view, callback)
    }

    override fun onHideCustomView() {
        callback?.onHideCustomView()
    }
}