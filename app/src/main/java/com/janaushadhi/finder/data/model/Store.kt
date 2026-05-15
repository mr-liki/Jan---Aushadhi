package com.janaushadhi.finder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Jan-Aushadhi Kendra (store) entity.
 */
@Entity(tableName = "stores")
data class Store(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String,
    val phone: String = "",
    val latitude: Double,
    val longitude: Double,
    val isOpenNow: Boolean = true,   // Simulated for prototype
    val openingTime: String = "09:00",
    val closingTime: String = "21:00",
    val rating: Float = 4.0f,
    val distanceKm: Double = 0.0     // Calculated at runtime
)
