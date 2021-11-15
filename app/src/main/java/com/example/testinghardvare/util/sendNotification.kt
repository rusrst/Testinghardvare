package com.example.testinghardvare.util

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.testinghardvare.R
import com.example.testinghardvare.SensorActivity

class notification {

    fun createChannel (chanID: String, chanName: String, context: Context){
        lateinit var notificationChannel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(chanID, chanName, NotificationManager.IMPORTANCE_HIGH).apply {
                setShowBadge(false)
                enableLights(true)
                lightColor = Color.GREEN
                enableVibration(true)
                description = "Time for save image"
            }
            val notificator = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificator.createNotificationChannel(notificationChannel)
        }
    }
    fun sendNotification(context: Context, chan_id: String, drawable: Int,
                         title: String, text: String, content_info: String, NOTIFICATION_ID: Int, intent: Intent)
    {
        val contentintent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, chan_id)
                .setContentIntent(contentintent)
                .setSmallIcon(drawable)
                .setContentTitle(title)
                .setContentText(text)
                .setContentInfo(content_info)
                .setAutoCancel(true)
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(NOTIFICATION_ID, builder.build())
    }

}