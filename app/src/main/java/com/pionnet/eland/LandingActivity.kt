package com.pionnet.eland

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import com.pionnet.eland.data.DataManager
import com.pionnet.eland.data.DataManager.EXTRA_DEEP_LINK
import com.pionnet.eland.data.DataManager.EXTRA_LINK
import com.pionnet.eland.data.DataManager.EXTRA_PUSH
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_CONTENT
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_IMAGE
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_TITLE
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_WEB_LINK
import com.pionnet.eland.data.DataManager.HOST_DEEPLINK
import com.pionnet.eland.data.DataManager.SCHEME


class LandingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val landingIntent = if (DataManager.isAppRunning) {
            Logger.w("app is alive")
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        } else {
            Logger.w("app is NOT alive")
            Intent(this, MainActivity::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        }

        prepareOtherLanding(landingIntent)
    }

    private fun prepareOtherLanding(landingIntent: Intent) {
        when {
            intent.getStringExtra(EXTRA_PUSH).equals("true") -> {
                landingIntent.apply {
                    putExtra(EXTRA_PUSH, true)
                    putExtra(EXTRA_PUSH_TITLE, intent.getStringExtra(EXTRA_PUSH_TITLE))
                    putExtra(EXTRA_PUSH_CONTENT, intent.getStringExtra(EXTRA_PUSH_CONTENT))
                    putExtra(EXTRA_PUSH_WEB_LINK, intent.getStringExtra(EXTRA_PUSH_WEB_LINK))
                    putExtra(EXTRA_PUSH_IMAGE, intent.getStringExtra(EXTRA_PUSH_IMAGE))
                }
                startLanding(landingIntent)
            }
            isDeepLink(intent.dataString) -> {
                handleFirebaseDeepLink(landingIntent)
            }
        }
    }

    private fun startLanding(landingIntent: Intent) {
        startActivity(landingIntent)
        finish()
    }

    private fun isDeepLink(url: String?): Boolean {
        if (url.isNullOrEmpty()) return false

        val uri = Uri.parse(url)
        return SCHEME.equals(uri.scheme, true) && HOST_DEEPLINK.equals(uri.host, true)
    }

    private fun handleFirebaseDeepLink(landingIntent: Intent) {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                Logger.d("getDynamicLink : $deepLink")
                landingIntent.apply {
                    putExtra(EXTRA_DEEP_LINK, true)
                    putExtra(EXTRA_LINK, deepLink.toString())
                }

                startLanding(landingIntent)
            }
            .addOnFailureListener(this) { e -> Logger.d( "getDynamicLink:onFailure $e") }
    }
}