package com.example.virtualfridge

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Singleton
import com.example.virtualfridge.R as VirtualfridgeR

@Singleton
class VfFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.notification?.let {
            val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, "Your Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationChannel.description = ""
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                notificationChannel.enableVibration(true)
                mNotificationManager.createNotificationChannel(notificationChannel)
            }

            // to diaplay notification in DND Mode
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
                channel.canBypassDnd()
            }

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)

            notificationBuilder.setAutoCancel(true)
                .setContentTitle(it.body)
                .setContentText(it.body)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(VirtualfridgeR.drawable.ic_settings)
                .setAutoCancel(true)


            mNotificationManager.notify(1000, notificationBuilder.build())
        }
        sendRegistrationToServer("")
    }

    private fun sendRegistrationToServer(token: String) =
        (application as VfApplication).refreshNotificationToken(token)

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "VirtualFridge_channel"
    }
}