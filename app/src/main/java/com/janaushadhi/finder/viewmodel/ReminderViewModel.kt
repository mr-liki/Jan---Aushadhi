package com.janaushadhi.finder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.janaushadhi.finder.data.database.AppDatabase
import com.janaushadhi.finder.data.model.Reminder
import com.janaushadhi.finder.data.repository.ReminderRepository
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReminderRepository
    val activeReminders: LiveData<List<Reminder>>
    val allReminders: LiveData<List<Reminder>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ReminderRepository(database.reminderDao())
        activeReminders = repository.getActiveReminders().asLiveData()
        allReminders = repository.getAllReminders().asLiveData()
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.addReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.updateReminder(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.deleteReminder(reminder)
        }
    }

    fun toggleReminderActive(id: Int, isActive: Boolean) {
        viewModelScope.launch {
            repository.setReminderActive(id, isActive)
        }
    }
}
