package com.janaushadhi.finder.data.repository

import com.janaushadhi.finder.data.dao.MedicineDao
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.utils.FuzzySearch
import kotlinx.coroutines.flow.Flow

class MedicineRepository(private val dao: MedicineDao) {

    fun getAllMedicines(): Flow<List<Medicine>> = dao.getAllMedicines()

    fun getFavoriteMedicines(): Flow<List<Medicine>> = dao.getFavoriteMedicines()

    fun getMedicinesByCategory(category: String): Flow<List<Medicine>> =
        dao.getMedicinesByCategory(category)

    suspend fun getMedicineById(id: Int): Medicine? = dao.getMedicineById(id)

    suspend fun getAllCategories(): List<String> = dao.getAllCategories()

    /**
     * Searches medicines using both SQL LIKE and fuzzy matching.
     * Returns results sorted by relevance score.
     */
    suspend fun searchMedicines(query: String): List<Medicine> {
        if (query.isBlank()) return emptyList()

        // First get SQL results (fast, handles exact/partial matches)
        val sqlResults = dao.searchMedicines(query.trim())

        // If we have good SQL results, return them sorted by fuzzy score
        if (sqlResults.isNotEmpty()) {
            return sqlResults.sortedByDescending { medicine ->
                maxOf(
                    FuzzySearch.score(query, medicine.brandName),
                    FuzzySearch.score(query, medicine.genericName),
                    FuzzySearch.score(query, medicine.saltComposition)
                )
            }
        }

        // Fallback: fuzzy search across all medicines for typo handling
        return fuzzySearchAll(query)
    }

    /**
     * Full fuzzy search across all medicines — used when SQL returns no results.
     */
    private suspend fun fuzzySearchAll(query: String): List<Medicine> {
        // Get all medicines and apply fuzzy matching
        val allMedicines = mutableListOf<Medicine>()
        dao.getAllMedicines().collect { allMedicines.addAll(it) }

        return allMedicines
            .filter { medicine ->
                FuzzySearch.matches(query, medicine.brandName) ||
                FuzzySearch.matches(query, medicine.genericName) ||
                FuzzySearch.matches(query, medicine.saltComposition)
            }
            .sortedByDescending { medicine ->
                maxOf(
                    FuzzySearch.score(query, medicine.brandName),
                    FuzzySearch.score(query, medicine.genericName)
                )
            }
            .take(20)
    }

    suspend fun toggleFavorite(medicine: Medicine) {
        dao.updateFavorite(medicine.id, !medicine.isFavorite)
    }

    suspend fun updateMedicine(medicine: Medicine) = dao.updateMedicine(medicine)
}
