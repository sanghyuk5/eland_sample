package com.pionnet.eland

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pionnet.eland.data.DataManager.EXTRA_PUSH
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_CONTENT
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_DATE
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_IMAGE
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_TITLE
import com.pionnet.eland.data.DataManager.EXTRA_PUSH_WEB_LINK
import java.text.SimpleDateFormat

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            sendNotification(remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            //it.body?.let { body -> sendNotification(body) }
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun sendNotification(data: MutableMap<String, String>) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val intent = Intent(this, LandingActivity::class.java).apply {
            putExtra(EXTRA_PUSH, data[EXTRA_PUSH])
            putExtra(EXTRA_PUSH_DATE, dateFormat.format(System.currentTimeMillis()))
            putExtra(EXTRA_PUSH_TITLE, data[EXTRA_PUSH_TITLE])
            putExtra(EXTRA_PUSH_CONTENT, data[EXTRA_PUSH_CONTENT])
            putExtra(EXTRA_PUSH_WEB_LINK, data[EXTRA_PUSH_WEB_LINK])
            putExtra(EXTRA_PUSH_IMAGE, data[EXTRA_PUSH_IMAGE])
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "fcm_default_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(data[EXTRA_PUSH_TITLE])
            .setContentText(data[EXTRA_PUSH_CONTENT])
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}