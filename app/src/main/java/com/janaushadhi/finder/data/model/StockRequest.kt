package com.janaushadhi.finder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Simulated stock availability request to a Jan-Aushadhi store.
 */
@Entity(tableName = "stock_requests")
data class StockRequest(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val storeId: Int,
    val storeName: String,
    val medicineName: String,
    val genericName: String,
    val requestTime: Long = System.currentTimeMillis(),
    val status: StockStatus = StockStatus.PENDING,
    val responseMessage: String = ""
)

enum class StockStatus {
    PENDING,
    IN_STOCK,
    OUT_OF_STOCK,
    LIMITED_STOCK
}
