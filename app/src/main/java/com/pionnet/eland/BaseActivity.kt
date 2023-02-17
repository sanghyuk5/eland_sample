package com.pionnet.eland

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.orhanobut.logger.Logger
import com.pionnet.eland.data.DataManager.EXTRA_LINK
import com.pionnet.eland.ui.goodsdetail.GoodsDetailActivity
import com.pionnet.eland.ui.leftmenu.LeftMenuActivity
import com.pionnet.eland.ui.push.PushListActivity
import com.pionnet.eland.ui.search.SearchActivity
import com.pionnet.eland.ui.search.searchCamera.SearchCameraActivity
import com.pionnet.eland.utils.*

open class BaseActivity : AppCompatActivity() {

    private val permissionHelper = PermissionGrantHelper(this)
    private val imagePermissions = arrayOf(
        Manifest.permission.CAMERA
    )


    private val resultNavToWeb = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        onResultNavToWeb(it.resultCode)
    }

    protected fun isSavedInstanceState(savedInstanceState: Bundle?): Boolean {
        return if (savedInstanceState != null) {
            Logger.d("savedInstanceState is NOT null.")
            val intent = Intent(this, MainActivity::class.java)
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
            LinkEventType.SEARCH -> navToSearch(linkEvent.data)
            LinkEventType.DEFAULT -> navToDefault(linkEvent.url)
            LinkEventType.DIAL -> navToDial(linkEvent.url)
            LinkEventType.PUSH_LIST -> navToPush()
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

    private fun navToSearch(data: String?) {
        if (isNetworkAvailable(this)) {
            startActivity(Intent(this, SearchActivity::class.java).putExtra(EXTRA_LINK, data))
        } else {
            dialogAlert(this, getString(R.string.msg_network_error))
        }
    }

    fun navToSearchCamera() {
        if (isNetworkAvailable(this)) {
            if (permissionHelper.isAllGranted(imagePermissions)) {
                startActivity(Intent(this, SearchCameraActivity::class.java))
            } else {
                dialogConfirm(this, "카메라 및 사진/미디어에 엑세스 하도록 접근 권한을 허용해야 합니다.",
                    okListener = {
                        if (permissionHelper.isAllGranted(imagePermissions)) {
                            startActivity(Intent(this, SearchCameraActivity::class.java))
                        } else {
                            val rationales = imagePermissions.filterIndexed { index, s ->
                                ActivityCompat.shouldShowRequestPermissionRationale(this, s)
                            }
                            if (rationales.isNotEmpty()) {
                                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    .setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)))
                            } else {
                                permissionHelper.checkMultiplePermissionsAndAction(imagePermissions,
                                    grantedCallback = {
                                        Logger.d("all granted.")
                                    }
                                )
                            }
                        }
                    }
                )
            }
        } else {
            dialogAlert(this, getString(R.string.msg_network_error))
        }
    }

    private fun navToDefault(url: String?) {
        if (url.isNullOrEmpty()) {
            debugToast(this, "empty url")
            return
        }

        if (url.isGoodsDetailUrl) {
            navToGoodsDetail(url)
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

    private fun navToGoodsDetail(url: String) {
        if (isNetworkAvailable(this)) {
            startActivity(Intent(this, GoodsDetailActivity::class.java).putExtra(EXTRA_LINK, url.withBaseUrl))
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

    private fun navToPush() {
        if (isNetworkAvailable(this)) {
            startActivity(Intent(this, PushListActivity::class.java))
        } else {
            dialogAlert(this, getString(R.string.msg_network_error))
        }
    }
}