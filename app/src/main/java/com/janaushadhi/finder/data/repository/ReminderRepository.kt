package com.janaushadhi.finder.data.repository

import com.janaushadhi.finder.data.dao.ReminderDao
import com.janaushadhi.finder.data.model.Reminder
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val dao: ReminderDao) {

    fun getActiveReminders(): Flow<List<Reminder>> = dao.getActiveReminders()

    fun getAllReminders(): Flow<List<Reminder>> = dao.getAllReminders()

    suspend fun getReminderById(id: Int): Reminder? = dao.getReminderById(id)

    suspend fun getDueReminders(): List<Reminder> =
        dao.getDueReminders(System.currentTimeMillis())

    suspend fun addReminder(reminder: Reminder): Long = dao.insertReminder(reminder)

    suspend fun updateReminder(reminder: Reminder) = dao.updateReminder(reminder)

    suspend fun deleteReminder(reminder: Reminder) = dao.deleteReminder(reminder)

    suspend fun deleteReminderById(id: Int) = dao.deleteReminderById(id)

    suspend fun setReminderActive(id: Int, isActive: Boolean) =
        dao.setReminderActive(id, isActive)
}
