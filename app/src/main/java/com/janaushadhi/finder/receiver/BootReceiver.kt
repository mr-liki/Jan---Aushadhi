package com.janaushadhi.finder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.janaushadhi.finder.worker.ReminderWorker

/**
 * Reschedules reminders after device reboot.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            ReminderWorker.schedule(context)
        }
    }
}
