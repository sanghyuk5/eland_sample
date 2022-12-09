package com.pionnet.eland.ui.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Message
import android.webkit.*
import androidx.annotation.RequiresApi
import com.orhanobut.logger.Logger
import com.pionnet.eland.utils.debugToast
import com.pionnet.eland.utils.dialogConfirm

class ElandWebViewClient(context: Context) : WebViewClient() {
    private var callback: Callback? = null
    private var currentUrl: String = ""

    interface Callback {
        fun onLoadAtNewWindow(url: String, useLayer: Boolean,
                              usePostMethod: Boolean, postData: String = "") {
            Logger.d("default onLoadAtNewWindow: $url")
        }
        fun onPageStarted(url: String) {
            Logger.d("default onPageStarted: $url")
        }
        fun onPageFinished(url: String) {
            Logger.d("default onPageFinished: $url")
        }
        fun onSivScheme(url: String) {
            Logger.d("default onSivScheme: $url")
        }
        fun onReceivedError(view: WebView?, request: WebResourceRequest?, errorCode: String) {
            Logger.d("default onReceivedError: $errorCode")
        }
    }

    init {
        if (context is Callback) {
            setCallback(context)
        }
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        url?.let {
            callback?.onPageStarted(it)

//            if (!it.isLoginUrl) {
//                currentUrl = it
//            }
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        url?.let {
            callback?.onPageFinished(url)
        }
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        callback?.onReceivedError(view, request,
            error?.let {
                "${it.errorCode}: ${it.description}"
            } ?: ""
        )
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
        callback?.onReceivedError(view, request,
            errorResponse?.let {
                "${it.statusCode}: ${it.reasonPhrase}"
            } ?: ""
        )
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        // if fired, use handler.proceed() with alertDialog
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        super.onFormResubmission(view, dontResend, resend)
        // if need, use resend.sendToTarget()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return handleUrl(view, url)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return handleUrl(view, request?.url.toString())
    }

    private fun handleUrl(view: WebView?, url: String?): Boolean {
        Logger.d("shouldOverrideUrl: $url")
        val context = view?.context

        if (url.isNullOrBlank()) return false

//        if (handleSpecificUrl(context, url)) return true

//        if (url.isSivScheme) {
//            callback?.onSivScheme(url)
//            return true
//        }


        if (url.startsWith("intent:", ignoreCase = true)) {
            return handleIntentUrl(context, url)
        }

//        if (url.isGoodsDetailUrl) {
//            return if (equalGoodsUrl(url)) {
//                false
//            }
//            else {
//                callback?.onLoadAtNewWindow(url, useLayer = false, usePostMethod = false)
//                true
//            }
//        }

        //if (url.isNetworkUrl()) return false

        return if (handleExtraUrl(context, url)) {
            true
        } else {
            context?.let {
                debugToast(it, "NOT handled url: $url");
            }

            false
        }
    }

//    fun equalGoodsUrl(goodsUrl: String): Boolean {
//        if (!currentUrl.isGoodsDetailUrl)
//            return false
//
//        val currentGoods = Uri.parse(currentUrl).getQueryParameter("goods_no")
//        val newGoods = Uri.parse(goodsUrl).getQueryParameter("goods_no")
//        return (currentGoods == newGoods)
//    }

//    private fun handleSpecificUrl(context: Context?, url: String): Boolean {
//        if (url.startsWith("http://www.sikorea.co.kr", ignoreCase = true)
//            || url.startsWith("https://www.sikorea.co.kr", ignoreCase = true)
//            || url.contains("ftc.go.kr", ignoreCase = true)
//        ) {
//            return handleExtraUrl(context, url)
//        }
//
//        val mainTabScheme = url.matchedMainTabScheme
//        if (!mainTabScheme.isNullOrBlank()) {
//            Logger.d("matched main tab scheme: $mainTabScheme, $url")
//            if (TabItem.TabType.from(mainTabScheme) == TabItem.TabType.Home) {
//                callback?.onSivScheme("$SIV_SCHEME://$HOST_NAVIGATOR?action=$HOST_NAVIGATOR_ACTION_HOME")
//            } else {
//                callback?.onSivScheme("$SIV_SCHEME://$HOST_MAIN?tab=$mainTabScheme")
//            }
//
//            return true
//        }
//
//        return false
//    }

    private fun handleIntentUrl(context: Context?, url: String): Boolean {
        if (context == null) return false

        if (url.startsWith("intent:storylink") || url.startsWith("intent:kakaolink")) {
            handleWelKnownIntent(context, url)
            return true
        } else {
            return try {
                context.startActivity(
                    Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
                        addCategory(Intent.CATEGORY_BROWSABLE)
                        component = null
                        selector = null
                    }
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    private fun handleWelKnownIntent(context: Context, url: String) {
        val uri = Uri.parse(url.replace("intent:", ""))
        try {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            dialogConfirm(context, "앱이 설치되어 있지 않습니다.\\n플레이스토어로 이동 하시겠습니까?",
                okListener = {
                    var target: String? = null
                    if (url.startsWith("intent:storylink")) {
                        target = "com.kakao.story"
                    } else if (url.startsWith("intent:kakaolink")) {
                        target = "com.kakao.talk"
                    }
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=$target")
                    context.startActivity(intent)
                }
            )
        }
    }

    private fun handleExtraUrl(context: Context?, url: String): Boolean {
        try {
            context?.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

}
