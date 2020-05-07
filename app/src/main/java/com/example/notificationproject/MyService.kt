package com.example.notificationproject

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

class MyService : Service() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            //do work that service needs to do
            val value1 = intent.getStringExtra("key1")
            val value2 = intent.getStringExtra("key2")
            if (value1 != null) {
                makeNotification()
            } else if (value2 != null) {
                makeNotification2()
            }
        }
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeNotification() {
        var notificationChannel: NotificationChannel
        var builder: Notification.Builder
        val channelID = "com.example.notificationproject"
        val description = "Test notification"

        var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


        val contentView = RemoteViews(packageName, R.layout.notification_layout)
        contentView.setTextViewText(R.id.tv_title, "Testing")
        contentView.setTextViewText(R.id.tv_content, "Test Notification")

        notificationChannel =
            NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(this, channelID)
            .setContent(contentView)
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(this)) {
            //notificationId is a unique int for each notification that you must define
            notify(1234, builder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeNotification2() {
        var notificationChannel: NotificationChannel
        var builder: Notification.Builder
        val channelID = "com.example.notificationproject"
        val description = "Test notification"

        var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


        val contentView = RemoteViews(packageName, R.layout.notification_layout2)
        contentView.setTextViewText(R.id.tv_title, "Me")
        contentView.setTextViewText(R.id.tv_content, "Test Message")

        notificationChannel =
            NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(this, channelID)
            .setContent(contentView)
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        with(NotificationManagerCompat.from(this)) {
            //notificationId is a unique int for each notification that you must define
            notify(1234, builder.build())
        }
    }
}