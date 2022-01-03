package com.arhamsoft.online.transferimunityclone.utils.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.arhamsoft.online.transferimunityclone.R
import com.arhamsoft.online.transferimunityclone.activities.UploadingFilesListActivity


class NotificationUtils {

    companion object {

        private var CHANNEL_ID: String = "Channel_Id"
        private var CHANNEL_NAME: String = "Channel_Name"
        private var CHECK: Boolean = false


        fun createNotification(context: Context, title: String, time: String) {

            createNotificationChannel(context)
            CHECK = true

            val intent = Intent(context, UploadingFilesListActivity::class.java)

            val pendingIntent = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(System.currentTimeMillis().toInt(), PendingIntent.FLAG_IMMUTABLE)
            }
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            builder.setSmallIcon(R.drawable.ic_notification_icon)
            builder.setContentTitle(title)
            builder.setContentText(time)
            builder.setContentIntent(pendingIntent)
            builder.setAutoCancel(true)
            builder.priority = NotificationCompat.PRIORITY_DEFAULT



            with(NotificationManagerCompat.from(context)) {
                notify(
                    System.currentTimeMillis().toInt(), builder.build()
                )
            }
        }

        private fun createNotificationChannel(context: Context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    lightColor = Color.BLUE
                    enableLights(true)
                }

                val manager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                manager.createNotificationChannel(channel)
            }

        }
    }

}