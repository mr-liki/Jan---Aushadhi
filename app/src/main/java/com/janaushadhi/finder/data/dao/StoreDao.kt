package com.janaushadhi.finder.data.dao

import androidx.room.*
import com.janaushadhi.finder.data.model.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {

    @Query("SELECT * FROM stores ORDER BY name ASC")
    fun getAllStores(): Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE id = :id")
    suspend fun getStoreById(id: Int): Store?

    @Query("""
        SELECT * FROM stores 
        WHERE ((:lat - latitude) * (:lat - latitude) + (:lng - longitude) * (:lng - longitude)) 
        <= (:radiusDeg * :radiusDeg)
        ORDER BY ((:lat - latitude) * (:lat - latitude) + (:lng - longitude) * (:lng - longitude)) ASC
    """)
    suspend fun getStoresNearby(lat: Double, lng: Double, radiusDeg: Double): List<Store>

    @Query("SELECT * FROM stores WHERE name LIKE '%' || :query || '%' OR city LIKE '%' || :query || '%'")
    suspend fun searchStores(query: String): List<Store>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(store: Store)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: List<Store>)

    @Update
    suspend fun updateStore(store: Store)

    @Delete
    suspend fun deleteStore(store: Store)

    @Query("SELECT COUNT(*) FROM stores")
    suspend fun getStoreCount(): Int
}
