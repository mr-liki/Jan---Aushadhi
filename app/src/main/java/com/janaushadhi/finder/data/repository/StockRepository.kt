package com.janaushadhi.finder.data.repository

import com.janaushadhi.finder.data.dao.StockRequestDao
import com.janaushadhi.finder.data.model.StockRequest
import com.janaushadhi.finder.data.model.StockStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class StockRepository(private val dao: StockRequestDao) {

    fun getAllRequests(): Flow<List<StockRequest>> = dao.getAllRequests()

    /**
     * Sends a simulated stock availability request.
     * Simulates a network delay and returns a random stock status.
     */
    suspend fun sendStockRequest(
        storeId: Int,
        storeName: String,
        medicineName: String,
        genericName: String
    ): StockRequest {
        // Insert pending request
        val request = StockRequest(
            storeId = storeId,
            storeName = storeName,
            medicineName = medicineName,
            genericName = genericName,
            status = StockStatus.PENDING
        )
        val id = dao.insertRequest(request)

        // Simulate network delay (1-2 seconds)
        delay(1500)

        // Simulate response (weighted random)
        val statuses = listOf(
            StockStatus.IN_STOCK, StockStatus.IN_STOCK, StockStatus.IN_STOCK,
            StockStatus.LIMITED_STOCK, StockStatus.LIMITED_STOCK,
            StockStatus.OUT_OF_STOCK
        )
        val status = statuses.random()
        val message = when (status) {
            StockStatus.IN_STOCK -> "✅ $genericName is available at $storeName. Visit the store to purchase."
            StockStatus.LIMITED_STOCK -> "⚠️ Limited stock of $genericName available at $storeName. Hurry!"
            StockStatus.OUT_OF_STOCK -> "❌ $genericName is currently out of stock at $storeName. Try another store."
            StockStatus.PENDING -> "Request pending..."
        }

        val updatedRequest = request.copy(
            id = id.toInt(),
            status = status,
            responseMessage = message
        )
        dao.updateRequest(updatedRequest)
        return updatedRequest
    }

    suspend fun deleteRequest(id: Int) = dao.deleteRequest(id)
}
