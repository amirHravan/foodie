package com.ravan.foodie.domain.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.ravan.foodie.domain.util.SharedPrefKeys
import java.util.Calendar

const val NOTIFICATION_CHANNEL_ID = "FOODIE_LOCAL_CHANNEL"
const val NOTIFICATION_CHANNEL_NAME = "LOCAL_CHANNEL"

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for reminders of foodie"
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

fun setAlarm(context: Context, hour: Int, minute: Int, dayOfWeek: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            dayOfWeek,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, dayOfWeek)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (timeInMillis < System.currentTimeMillis()) {
            add(Calendar.WEEK_OF_YEAR, 1)
        }
    }

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY * 7,
        pendingIntent
    )
}

fun setAlarmsBasedOnPreference(
    context: Context,
) {
    if (isNotificationEnabled(context)) {
        setAlarm(context, 20, 0, Calendar.TUESDAY)
        setAlarm(context, 15, 0, Calendar.WEDNESDAY)
    } else {
        cancelAlarm(context, Calendar.TUESDAY)
        cancelAlarm(context, Calendar.WEDNESDAY)
    }
}

private fun cancelAlarm(context: Context, dayOfWeek: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        dayOfWeek,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )
    alarmManager.cancel(pendingIntent)
}

private fun isNotificationEnabled(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(SharedPrefKeys.NotificationsEnabled.key, true)
}