package com.janaushadhi.finder.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

/**
 * Utility class for place search and geocoding functionality
 */
object PlaceSearchUtils {

    private const val TAG = "PlaceSearchUtils"

    /**
     * Data class to hold place search results
     */
    data class PlaceResult(
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val address: String
    )

    /**
     * Search for places using Android's built-in Geocoder
     * This provides basic place search without requiring Google Places API
     */
    suspend fun searchPlaces(
        context: Context,
        query: String,
        maxResults: Int = 5
    ): List<PlaceResult> = withContext(Dispatchers.IO) {
        val results = mutableListOf<PlaceResult>()
        
        try {
            if (!Geocoder.isPresent()) {
                Log.w(TAG, "Geocoder not available on this device")
                return@withContext results
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocationName(query, maxResults)
            
            addresses?.forEach { address ->
                val placeName = getPlaceName(address)
                val fullAddress = getFullAddress(address)
                
                results.add(
                    PlaceResult(
                        name = placeName,
                        latitude = address.latitude,
                        longitude = address.longitude,
                        address = fullAddress
                    )
                )
            }
            
            Log.d(TAG, "Found ${results.size} places for query: $query")
            
        } catch (e: IOException) {
            Log.e(TAG, "Geocoding failed for query: $query", e)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during place search", e)
        }
        
        return@withContext results
    }

    /**
     * Get coordinates for a specific place name
     */
    suspend fun getCoordinatesForPlace(
        context: Context,
        placeName: String
    ): PlaceResult? = withContext(Dispatchers.IO) {
        try {
            if (!Geocoder.isPresent()) {
                return@withContext null
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocationName(placeName, 1)
            
            return@withContext addresses?.firstOrNull()?.let { address ->
                PlaceResult(
                    name = getPlaceName(address),
                    latitude = address.latitude,
                    longitude = address.longitude,
                    address = getFullAddress(address)
                )
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get coordinates for place: $placeName", e)
            return@withContext null
        }
    }

    /**
     * Get address from coordinates (reverse geocoding)
     */
    suspend fun getAddressFromCoordinates(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String? = withContext(Dispatchers.IO) {
        try {
            if (!Geocoder.isPresent()) {
                return@withContext null
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            
            return@withContext addresses?.firstOrNull()?.let { address ->
                getFullAddress(address)
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get address from coordinates", e)
            return@withContext null
        }
    }

    /**
     * Extract a meaningful place name from Address object
     */
    private fun getPlaceName(address: Address): String {
        return when {
            !address.locality.isNullOrEmpty() -> address.locality
            !address.subAdminArea.isNullOrEmpty() -> address.subAdminArea
            !address.adminArea.isNullOrEmpty() -> address.adminArea
            !address.countryName.isNullOrEmpty() -> address.countryName
            else -> "Unknown Location"
        }
    }

    /**
     * Build a full address string from Address object
     */
    private fun getFullAddress(address: Address): String {
        val addressParts = mutableListOf<String>()
        
        // Add feature name (specific location)
        if (!address.featureName.isNullOrEmpty() && address.featureName != address.locality) {
            addressParts.add(address.featureName)
        }
        
        // Add locality (city)
        if (!address.locality.isNullOrEmpty()) {
            addressParts.add(address.locality)
        }
        
        // Add admin area (state)
        if (!address.adminArea.isNullOrEmpty()) {
            addressParts.add(address.adminArea)
        }
        
        // Add country
        if (!address.countryName.isNullOrEmpty()) {
            addressParts.add(address.countryName)
        }
        
        return addressParts.joinToString(", ")
    }

    /**
     * Predefined popular Indian cities and landmarks for quick search suggestions
     */
    fun getPopularCities(): List<String> {
        return listOf(
            // Major Cities
            "Delhi", "New Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata", "Hyderabad",
            "Pune", "Ahmedabad", "Jaipur", "Surat", "Lucknow", "Kanpur",
            "Nagpur", "Indore", "Thane", "Bhopal", "Visakhapatnam", "Pimpri-Chinchwad",
            "Patna", "Vadodara", "Ghaziabad", "Ludhiana", "Agra", "Nashik",
            "Faridabad", "Meerut", "Rajkot", "Kalyan-Dombivali", "Vasai-Virar",
            "Varanasi", "Srinagar", "Aurangabad", "Dhanbad", "Amritsar",
            
            // State Capitals
            "Thiruvananthapuram", "Gandhinagar", "Panaji", "Shimla", "Ranchi",
            "Bhubaneswar", "Chandigarh", "Dehradun", "Raipur", "Imphal",
            "Shillong", "Aizawl", "Kohima", "Agartala", "Itanagar",
            
            // Popular Areas/Landmarks
            "Connaught Place Delhi", "Marine Drive Mumbai", "MG Road Bangalore",
            "Anna Nagar Chennai", "Park Street Kolkata", "Banjara Hills Hyderabad",
            "Koregaon Park Pune", "Satellite Ahmedabad", "Pink City Jaipur"
        )
    }

    /**
     * Filter popular cities based on search query
     */
    fun filterPopularCities(query: String): List<String> {
        if (query.length < 2) return emptyList()
        
        return getPopularCities().filter { city ->
            city.lowercase().contains(query.lowercase())
        }.take(5)
    }
}