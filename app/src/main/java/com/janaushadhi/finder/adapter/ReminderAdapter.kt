package com.janaushadhi.finder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.data.model.Reminder
import java.text.SimpleDateFormat
import java.util.*

class ReminderAdapter(
    private val onDeleteClick: (Reminder) -> Unit,
    private val onToggleActive: (Reminder, Boolean) -> Unit
) : ListAdapter<Reminder, ReminderAdapter.ReminderViewHolder>(ReminderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val medicineName: TextView = itemView.findViewById(R.id.tv_medicine_name)
        private val genericName: TextView = itemView.findViewById(R.id.tv_generic_name)
        private val refillDate: TextView = itemView.findViewById(R.id.tv_refill_date)
        private val daysUntil: TextView = itemView.findViewById(R.id.tv_days_until)
        private val activeSwitch: Switch = itemView.findViewById(R.id.switch_active)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete)

        fun bind(reminder: Reminder) {
            medicineName.text = reminder.medicineName
            genericName.text = reminder.genericName

            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            refillDate.text = dateFormat.format(Date(reminder.refillDate))

            val daysRemaining = ((reminder.refillDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
            daysUntil.text = when {
                daysRemaining < 0 -> "Overdue"
                daysRemaining == 0 -> "Due today"
                else -> "Due in $daysRemaining days"
            }

            activeSwitch.isChecked = reminder.isActive
            activeSwitch.setOnCheckedChangeListener { _, isChecked ->
                onToggleActive(reminder, isChecked)
            }

            deleteButton.setOnClickListener { onDeleteClick(reminder) }
        }
    }

    private class ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem == newItem
        }
    }
}
