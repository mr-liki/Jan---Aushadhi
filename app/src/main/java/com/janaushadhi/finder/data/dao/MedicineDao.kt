package com.janaushadhi.finder.data.dao

import androidx.room.*
import com.janaushadhi.finder.data.model.Medicine
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Query("SELECT * FROM medicines ORDER BY brandName ASC")
    fun getAllMedicines(): Flow<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE id = :id")
    suspend fun getMedicineById(id: Int): Medicine?

    @Query("""
        SELECT * FROM medicines 
        WHERE brandName LIKE '%' || :query || '%' 
        OR genericName LIKE '%' || :query || '%'
        OR saltComposition LIKE '%' || :query || '%'
        OR category LIKE '%' || :query || '%'
        ORDER BY 
            CASE WHEN brandName LIKE :query || '%' THEN 0 ELSE 1 END,
            brandName ASC
        LIMIT 50
    """)
    suspend fun searchMedicines(query: String): List<Medicine>

    @Query("SELECT * FROM medicines WHERE category = :category ORDER BY brandName ASC")
    fun getMedicinesByCategory(category: String): Flow<List<Medicine>>

    @Query("SELECT DISTINCT category FROM medicines ORDER BY category ASC")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * FROM medicines WHERE isFavorite = 1 ORDER BY brandName ASC")
    fun getFavoriteMedicines(): Flow<List<Medicine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicines: List<Medicine>)

    @Update
    suspend fun updateMedicine(medicine: Medicine)

    @Query("UPDATE medicines SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    @Query("SELECT COUNT(*) FROM medicines")
    suspend fun getMedicineCount(): Int
}
