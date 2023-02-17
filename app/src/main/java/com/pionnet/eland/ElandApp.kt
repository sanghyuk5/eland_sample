package com.pionnet.eland

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.pionnet.eland.data.room.PushDatabase


class ElandApp : Application() {
    companion object {
        @Volatile
        lateinit var instance: ElandApp

        val appContext: Context
            get() = instance.applicationContext

        lateinit var database: PushDatabase
            private set
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
            initFlipper()
        }

        initRoomDataBase()
    }

    private var flipperInitialized = false

    private fun initFlipper() {
        SoLoader.init(appContext, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(appContext)) {
            val client = AndroidFlipperClient.getInstance(appContext)
            client.addPlugin(
                InspectorFlipperPlugin(
                    appContext.applicationContext,
                    DescriptorMapping.withDefaults()
                )
            )

            client.addPlugin(NetworkFlipperPlugin())

            client.addPlugin(SharedPreferencesFlipperPlugin(appContext))

            client.start()
        }

        flipperInitialized = true
    }

    private fun initRoomDataBase() {
        database = Room.databaseBuilder(appContext, PushDatabase::class.java, "data.db")
            .build()
    }
}