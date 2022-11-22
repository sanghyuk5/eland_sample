package com.pionnet.eland.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.pionnet.eland.BuildConfig

fun debugToast(context: Context, msg: String, tag: String? = "") {
    if (BuildConfig.DEBUG) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        Logger.t(tag).d(msg)
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        (nc != null &&
                (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)))
    } else {
        val activeNetworkInfo = cm.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

fun <T> MediatorLiveData<T>.addSourceList(vararg liveDataArgs: MutableLiveData<*>, onChanged: () -> T) {
    liveDataArgs.forEach {
        this.addSource(it) { value = onChanged() }
    }
}

fun getDisplaySize(context: Context): DisplayMetrics {
    var displayMetrics = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        displayMetrics = context.resources.displayMetrics
    } else {
        @Suppress("DEPRECATION")
        val display = (context as Activity).windowManager.defaultDisplay

        @Suppress("DEPRECATION")
        display.getMetrics(displayMetrics)
    }
    return displayMetrics
}