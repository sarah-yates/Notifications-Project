package com.example.notificationproject

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.notificationproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelID = "com.example.notification"
    private val description = "Test notification"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btn_notify.setOnClickListener {

            val intent = Intent(this, LauncherActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


            val contentView = RemoteViews(packageName, R.layout.notification_layout)
            contentView.setTextViewText(R.id.tv_title, "Title here")
            contentView.setTextViewText(R.id.tv_content, "Test Notification")

            notificationChannel =
                NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelID)
                .setContent(contentView)
                .setSmallIcon(R.drawable.notification_icon)
                //.setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.notification_icon))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)


            with(NotificationManagerCompat.from(this)) {
                //notificationId is a unique int for each notification that you must define
                notify(1234, builder.build())
            }
        }
    }
}
