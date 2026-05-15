package com.janaushadhi.finder.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.janaushadhi.finder.R
import com.janaushadhi.finder.adapter.ReminderAdapter
import com.janaushadhi.finder.data.model.Reminder
import com.janaushadhi.finder.viewmodel.ReminderViewModel
import java.util.*

class RemindersFragment : Fragment() {

    private lateinit var viewModel: ReminderViewModel
    private lateinit var adapter: ReminderAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ReminderViewModel::class.java]

        recyclerView = view.findViewById(R.id.recycler_view)
        emptyView = view.findViewById(R.id.empty_view)
        fab = view.findViewById(R.id.fab_add_reminder)

        setupRecyclerView()
        observeViewModel()

        fab.setOnClickListener {
            showAddReminderDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = ReminderAdapter(
            onDeleteClick = { reminder ->
                viewModel.deleteReminder(reminder)
            },
            onToggleActive = { reminder, isActive ->
                viewModel.toggleReminderActive(reminder.id, isActive)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.activeReminders.observe(viewLifecycleOwner) { reminders ->
            adapter.submitList(reminders)
            updateEmptyView(reminders.isEmpty())
        }
    }

    private fun updateEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun showAddReminderDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(year, month, day)
                // For demo purposes, create a simple reminder
                val reminder = Reminder(
                    medicineName = "Sample Medicine",
                    genericName = "Generic Name",
                    dosage = "1 tablet daily",
                    refillDate = calendar.timeInMillis,
                    reminderDaysBefore = 3
                )
                viewModel.addReminder(reminder)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
