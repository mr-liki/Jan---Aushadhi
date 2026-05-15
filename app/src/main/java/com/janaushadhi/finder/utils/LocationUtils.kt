package com.janaushadhi.finder.utils

import android.location.Location
import com.janaushadhi.finder.data.model.Store
import kotlin.math.*

object LocationUtils {

    /**
     * Calculates distance in km between two lat/lng points using Haversine formula.
     */
    fun distanceKm(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val earthRadius = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLng / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

    /**
     * Filters and sorts stores within a given radius (km) from user location.
     */
    fun getStoresWithinRadius(
        stores: List<Store>,
        userLat: Double,
        userLng: Double,
        radiusKm: Double = 10.0
    ): List<Store> {
        return stores
            .map { store ->
                val dist = distanceKm(userLat, userLng, store.latitude, store.longitude)
                store.copy(distanceKm = dist)
            }
            .filter { it.distanceKm <= radiusKm }
            .sortedBy { it.distanceKm }
    }

    /**
     * Converts degrees to approximate km (for Room DB query radius).
     * 1 degree ≈ 111 km
     */
    fun kmToDegrees(km: Double): Double = km / 111.0

    /**
     * Formats distance for display.
     */
    fun formatDistance(km: Double): String {
        return if (km < 1.0) {
            "${(km * 1000).toInt()} m"
        } else {
            String.format("%.1f km", km)
        }
    }
}
