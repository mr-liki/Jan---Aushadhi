package com.janaushadhi.finder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.switchmaterial.SwitchMaterial
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
        private val dosage: TextView = itemView.findViewById(R.id.tv_dosage)
        private val refillDate: TextView = itemView.findViewById(R.id.tv_refill_date)
        private val daysUntil: TextView = itemView.findViewById(R.id.tv_days_until_refill)
        private val activeSwitch: SwitchMaterial = itemView.findViewById(R.id.switch_active)
        private val deleteButton: ImageView = itemView.findViewById(R.id.iv_delete)

        fun bind(reminder: Reminder) {
            medicineName.text = reminder.medicineName
            dosage.text = reminder.dosage

            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            refillDate.text = "Refill on: ${dateFormat.format(Date(reminder.refillDate))}"

            val daysRemaining = ((reminder.refillDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)).toInt()
            daysUntil.text = when {
                daysRemaining < 0 -> "Overdue"
                daysRemaining == 0 -> "Due today"
                else -> "$daysRemaining days remaining"
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
