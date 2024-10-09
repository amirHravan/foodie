package com.ravan.foodie.domain.notification

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ravan.foodie.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(getRandomNotificationText(context))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        0
                    )
                }
                return
            }
            notify(0, builder.build())
        }
    }

    private fun getRandomNotificationText(context: Context): String {
        val notificationMessages = listOf(
            R.string.notification_remember_to_reserve_1,
            R.string.notification_remember_to_reserve_2,
            R.string.notification_remember_to_reserve_3,
            R.string.notification_remember_to_reserve_4,
            R.string.notification_remember_to_reserve_5,
            R.string.notification_remember_to_reserve_6,
            R.string.notification_remember_to_reserve_7,
            R.string.notification_remember_to_reserve_8,
            R.string.notification_remember_to_reserve_9,
        )
        return context.getString(notificationMessages.random()) + "\n غذات رو رزرو کن. \n"
    }

}
