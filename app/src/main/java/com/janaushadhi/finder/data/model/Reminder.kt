package com.janaushadhi.finder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Medicine refill reminder entity.
 */
@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val medicineName: String,
    val genericName: String,
    val dosage: String,            // e.g., "1 tablet twice daily"
    val refillDate: Long,          // Timestamp of next refill
    val reminderDaysBefore: Int = 3, // Remind X days before refill
    val isActive: Boolean = true,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
