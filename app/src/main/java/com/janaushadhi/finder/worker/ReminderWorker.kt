package com.janaushadhi.finder.worker

import android.content.Context
import androidx.work.*
import com.janaushadhi.finder.data.database.AppDatabase
import com.janaushadhi.finder.data.repository.ReminderRepository
import com.janaushadhi.finder.utils.NotificationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 * WorkManager worker that checks for due reminders and sends notifications.
 */
class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = AppDatabase.getDatabase(applicationContext)
            val repository = ReminderRepository(database.reminderDao())

            // Get reminders due today or overdue
            val dueReminders = repository.getDueReminders()

            dueReminders.forEach { reminder ->
                val daysUntil = ((reminder.refillDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
                NotificationUtils.showReminderNotification(
                    applicationContext,
                    reminder.id,
                    reminder.medicineName,
                    daysUntil
                )
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "ReminderCheckWork"

        /**
         * Schedules daily reminder checks at 9 AM.
         */
        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            val reminderRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
                1, TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                reminderRequest
            )
        }

        /**
         * Calculates delay until next 9 AM.
         */
        private fun calculateInitialDelay(): Long {
            val calendar = java.util.Calendar.getInstance()
            val now = calendar.timeInMillis
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 9)
            calendar.set(java.util.Calendar.MINUTE, 0)
            calendar.set(java.util.Calendar.SECOND, 0)
            if (calendar.timeInMillis <= now) {
                calendar.add(java.util.Calendar.DAY_OF_YEAR, 1)
            }
            return calendar.timeInMillis - now
        }
    }
}
