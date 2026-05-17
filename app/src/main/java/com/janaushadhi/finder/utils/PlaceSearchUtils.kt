package com.janaushadhi.finder.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
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
                // Return popular city matches as fallback
                return@withContext getPopularCityMatches(query, maxResults)
            }

            val geocoder = Geocoder(context, Locale.getDefault())
            
            // Add timeout to prevent ANR
            val addresses = try {
                withTimeout(5000) { // 5 second timeout
                    geocoder.getFromLocationName(query, maxResults)
                }
            } catch (e: kotlinx.coroutines.TimeoutCancellationException) {
                Log.w(TAG, "Geocoding timeout for query: $query")
                // Return popular city matches as fallback
                return@withContext getPopularCityMatches(query, maxResults)
            }
            
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
            // Return popular city matches as fallback
            return@withContext getPopularCityMatches(query, maxResults)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during place search", e)
            // Return popular city matches as fallback
            return@withContext getPopularCityMatches(query, maxResults)
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

    /**
     * Get popular city matches as PlaceResult objects with predefined coordinates
     */
    private fun getPopularCityMatches(query: String, maxResults: Int): List<PlaceResult> {
        val cityCoordinates = mapOf(
            "Delhi" to Pair(28.6139, 77.2090),
            "New Delhi" to Pair(28.6139, 77.2090),
            "Mumbai" to Pair(19.0760, 72.8777),
            "Bangalore" to Pair(12.9716, 77.5946),
            "Chennai" to Pair(13.0827, 80.2707),
            "Kolkata" to Pair(22.5726, 88.3639),
            "Hyderabad" to Pair(17.3850, 78.4867),
            "Pune" to Pair(18.5204, 73.8567),
            "Ahmedabad" to Pair(23.0225, 72.5714),
            "Jaipur" to Pair(26.9124, 75.7873),
            "Surat" to Pair(21.1702, 72.8311),
            "Lucknow" to Pair(26.8467, 80.9462),
            "Kanpur" to Pair(26.4499, 80.3319),
            "Nagpur" to Pair(21.1458, 79.0882),
            "Indore" to Pair(22.7196, 75.8577),
            "Bhopal" to Pair(23.2599, 77.4126),
            "Visakhapatnam" to Pair(17.6868, 83.2185),
            "Patna" to Pair(25.5941, 85.1376),
            "Vadodara" to Pair(22.3072, 73.1812),
            "Ludhiana" to Pair(30.9010, 75.8573),
            "Agra" to Pair(27.1767, 78.0081),
            "Nashik" to Pair(19.9975, 73.7898),
            "Meerut" to Pair(28.9845, 77.7064),
            "Rajkot" to Pair(22.3039, 70.8022),
            "Varanasi" to Pair(25.3176, 82.9739),
            "Srinagar" to Pair(34.0837, 74.7973),
            "Aurangabad" to Pair(19.8762, 75.3433),
            "Amritsar" to Pair(31.6340, 74.8723)
        )

        val matchingCities = cityCoordinates.filter { (city, _) ->
            city.lowercase().contains(query.lowercase())
        }.toList().take(maxResults)

        return matchingCities.map { (city, coordinates) ->
            PlaceResult(
                name = city,
                latitude = coordinates.first,
                longitude = coordinates.second,
                address = "$city, India"
            )
        }
    }
}