package com.pionnet.eland.utils

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.orhanobut.logger.Logger

typealias PermissionGrantedCallback = (() -> Unit)
/**
 * parameter shouldShowRequestPermissionRationale: Boolean
 */
typealias PermissionDeniedCallback = ((Boolean) -> Unit)
typealias MultiplePermissionDeniedCallback = ((Array<String>) -> Unit)

class PermissionGrantHelper(private val activity: AppCompatActivity) {

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Logger.d("권한 승인됨")
                permissionGrantedCallback?.invoke()
            } else {
                val shouldShowRequestPermissionRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        requestedPermission
                    )
                Logger.d("권한 거부됨 Rationale:$shouldShowRequestPermissionRationale")
                permissionDeniedCallback?.invoke(shouldShowRequestPermissionRationale)
            }
        }

    private val requestMultiplePermissionsLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { requestedPermissionMaps ->
            if (requestedPermissionMaps.isEmpty()) {
                Logger.d("권한 취소 됨")
                return@registerForActivityResult
            }
            if (null == requestedPermissionMaps.values.firstOrNull { it == false }) {
                Logger.d("권한 모두 승인됨")
                permissionGrantedCallback?.invoke()
            } else {
                handlePermissionsDenied(requestedPermissionMaps)
            }
        }

    private fun handlePermissionsDenied(requestedPermissionMaps: Map<String, Boolean>) {
        val denied = requestedPermissionMaps.filter { !it.value }

        val rationales = denied.filterKeys { key ->
            !ActivityCompat.shouldShowRequestPermissionRationale(
                activity, key
            )
        }
        if (rationales.isEmpty()) {
            // SIV 정책에 따라 NOP
            // requestMultiplePermissionsLauncher.launch(denied.keys.toTypedArray())
        } else {
            Logger.d("권한 거부됨 Rationales:${rationales.keys}")
            multiplePermissionDeniedCallback?.invoke(rationales.keys.toTypedArray())
        }
    }

    private var requestedPermission: String = ""
    private var permissionGrantedCallback: PermissionGrantedCallback? = null
    private var permissionDeniedCallback: PermissionDeniedCallback? = null


    private var multiplePermissionDeniedCallback: MultiplePermissionDeniedCallback? = null

    fun checkPermission(permission: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            activity,
            permission
        )
    }

    fun isAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (!checkPermission(permission))
                return false
        }
        return true
    }

    fun checkPermissionRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun checkPermissionAndAction(
        permission: String,
        grantedCallback: PermissionGrantedCallback,
        deniedCallback: PermissionDeniedCallback? = null
    ) {
        requestedPermission = permission
        permissionGrantedCallback = grantedCallback
        permissionDeniedCallback = deniedCallback

        when {
            checkPermission(permission) -> {
                grantedCallback()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                requestPermission(permission)
            }
            else -> {
                requestPermission(permission)
            }
        }
    }

    private fun requestPermission(
        permission: String
    ) {
        requestPermissionLauncher.launch(permission)
    }

    fun checkMultiplePermissionsAndAction(
        permissions: Array<out String>,
        grantedCallback: PermissionGrantedCallback,
        multipleDeniedCallback: MultiplePermissionDeniedCallback? = null
    ) {
        permissionGrantedCallback = grantedCallback
        multiplePermissionDeniedCallback = multipleDeniedCallback

        requestMultiplePermissionsLauncher.launch(permissions as Array<String>?)
    }
}