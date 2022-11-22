package com.pionnet.eland

import android.app.Application
import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class ElandApp : Application() {
    companion object {
        @Volatile
        lateinit var instance: ElandApp

        val appContext: Context
            get() = instance.applicationContext
    }
    override fun onCreate() {
        super.onCreate()
        instance = this

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        if (!flipperInitialized) {
            initFlipper(appContext)
        }
    }

    private var flipperInitialized = false

    private fun initFlipper(context: Context) {
        SoLoader.init(context, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            client.addPlugin(
                InspectorFlipperPlugin(
                    context.applicationContext,
                    DescriptorMapping.withDefaults()
                )
            )

            client.addPlugin(NetworkFlipperPlugin())

            client.addPlugin(SharedPreferencesFlipperPlugin(context))

            client.start()
        }

        flipperInitialized = true
    }
}