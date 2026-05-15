package com.janaushadhi.finder.data.repository

import com.janaushadhi.finder.data.dao.StoreDao
import com.janaushadhi.finder.data.model.Store
import com.janaushadhi.finder.utils.LocationUtils
import kotlinx.coroutines.flow.Flow

class StoreRepository(private val dao: StoreDao) {

    fun getAllStores(): Flow<List<Store>> = dao.getAllStores()

    suspend fun getStoreById(id: Int): Store? = dao.getStoreById(id)

    /**
     * Returns stores within radiusKm of the given location, sorted by distance.
     */
    suspend fun getStoresNearby(
        lat: Double,
        lng: Double,
        radiusKm: Double = 10.0
    ): List<Store> {
        val radiusDeg = LocationUtils.kmToDegrees(radiusKm)
        val candidates = dao.getStoresNearby(lat, lng, radiusDeg)

        // Precise Haversine filtering and distance calculation
        return candidates
            .map { store ->
                val dist = LocationUtils.distanceKm(lat, lng, store.latitude, store.longitude)
                store.copy(distanceKm = dist)
            }
            .filter { it.distanceKm <= radiusKm }
            .sortedBy { it.distanceKm }
    }

    /**
     * Returns all stores with distance calculated from user location.
     */
    suspend fun getAllStoresWithDistance(lat: Double, lng: Double): List<Store> {
        val allStores = mutableListOf<Store>()
        dao.getAllStores().collect { allStores.addAll(it) }
        return allStores.map { store ->
            val dist = LocationUtils.distanceKm(lat, lng, store.latitude, store.longitude)
            store.copy(distanceKm = dist)
        }.sortedBy { it.distanceKm }
    }

    suspend fun searchStores(query: String): List<Store> = dao.searchStores(query)
}
