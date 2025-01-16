package com.cangcang.constraintlayoutdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        findViewById<TextView>(R.id.center_block).setOnClickListener {
            triggerNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "1232",
                "CHANNEL_NEWs",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.setDescription("some channel")
            notificationChannel.setShowBadge(true)
            notificationChannel.enableVibration(true)
            notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            notificationChannel.importance = NotificationManager.IMPORTANCE_HIGH
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun triggerNotification() {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "1232")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setContentTitle("Notification Title")
                .setContentText("This is text, that will be shown as part of notification")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("This is text, that will be shown as part of notification")
                )
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setChannelId("1232")
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(
            123,
            builder.build()
        )
    }
}