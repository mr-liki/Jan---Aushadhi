package com.janaushadhi.finder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Medicine entity stored in Room DB.
 * Contains brand name, generic salt, prices, and category.
 */
@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val brandName: String,
    val genericName: String,       // Salt / generic equivalent
    val saltComposition: String,   // e.g., "Paracetamol 500mg"
    val brandedPrice: Double,      // MRP of branded medicine (₹)
    val genericPrice: Double,      // Jan-Aushadhi price (₹)
    val category: String,          // e.g., "Analgesic", "Antibiotic"
    val manufacturer: String = "",
    val dosageForm: String = "",   // Tablet, Syrup, Injection, etc.
    val strength: String = "",     // e.g., "500mg", "10mg"
    val uses: String = "",         // Brief description of uses
    val sideEffects: String = "",
    val isFavorite: Boolean = false
) {
    /** Savings amount in ₹ */
    val savings: Double get() = brandedPrice - genericPrice

    /** Savings percentage */
    val savingsPercent: Int get() = if (brandedPrice > 0)
        ((savings / brandedPrice) * 100).toInt() else 0
}
