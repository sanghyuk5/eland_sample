package com.pionnet.eland.ui.main.tabWeb

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.orhanobut.logger.Logger
import com.pionnet.eland.databinding.FragmentMainTabWebviewBinding
import com.pionnet.eland.ui.main.MainBaseFragment
import com.pionnet.eland.ui.webview.ElandWebViewClient

class MainWebViewFragment : MainBaseFragment() {

    private val binding: FragmentMainTabWebviewBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.d("hyuk here")

        binding.webView.loadUrl(url)
        binding.webView.setClientCallback(webViewCallback)
    }

    private val webViewCallback = object : ElandWebViewClient.Callback {

        var isPageFinished = false

        override fun onPageStarted(url: String) {
            super.onPageStarted(url)
            isPageFinished = false
        }

        override fun onPageFinished(url: String) {
            super.onPageFinished(url)
            isPageFinished = true

        }

        override fun onSivScheme(url: String) {
            val uri = Uri.parse(url)
        }
    }

    companion object {
        fun create(url: String?) =
            MainWebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_URL, url)
                }
            }
    }
}