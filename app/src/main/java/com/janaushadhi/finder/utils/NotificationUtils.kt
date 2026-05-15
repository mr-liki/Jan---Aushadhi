package com.janaushadhi.finder.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.janaushadhi.finder.R
import com.janaushadhi.finder.ui.main.MainActivity

object NotificationUtils {

    const val CHANNEL_ID_REMINDERS = "jan_aushadhi_reminders"
    const val CHANNEL_NAME_REMINDERS = "Medicine Refill Reminders"
    const val CHANNEL_DESC_REMINDERS = "Notifications for medicine refill reminders"

    /**
     * Creates the notification channel (required for Android 8.0+).
     */
    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_REMINDERS,
                CHANNEL_NAME_REMINDERS,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC_REMINDERS
                enableVibration(true)
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    /**
     * Shows a medicine refill reminder notification.
     */
    fun showReminderNotification(
        context: Context,
        notificationId: Int,
        medicineName: String,
        daysUntilRefill: Int
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to", "reminders")
        }
        val pendingIntent = PendingIntent.getActivity(
            context, notificationId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = "Medicine Refill Reminder"
        val message = when (daysUntilRefill) {
            0 -> "Time to refill $medicineName today!"
            1 -> "$medicineName refill due tomorrow"
            else -> "$medicineName refill due in $daysUntilRefill days"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_REMINDERS)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(notificationId, notification)
        } catch (e: SecurityException) {
            // Notification permission not granted
        }
    }
}
