package com.janaushadhi.finder.data.dao

import androidx.room.*
import com.janaushadhi.finder.data.model.StockRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface StockRequestDao {

    @Query("SELECT * FROM stock_requests ORDER BY requestTime DESC")
    fun getAllRequests(): Flow<List<StockRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: StockRequest): Long

    @Update
    suspend fun updateRequest(request: StockRequest)

    @Query("DELETE FROM stock_requests WHERE id = :id")
    suspend fun deleteRequest(id: Int)

    @Query("SELECT * FROM stock_requests WHERE id = :id")
    suspend fun getRequestById(id: Int): StockRequest?
}
