package com.janaushadhi.finder.utils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Utility class for place search functionality
 * Uses predefined city data to avoid ANR issues with Geocoder
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
     * Comprehensive list of Indian cities with coordinates
     */
    private val indianCities = mapOf(
        // Major Metropolitan Cities
        "Delhi" to PlaceResult("Delhi", 28.6139, 77.2090, "Delhi, India"),
        "New Delhi" to PlaceResult("New Delhi", 28.6139, 77.2090, "New Delhi, Delhi, India"),
        "Mumbai" to PlaceResult("Mumbai", 19.0760, 72.8777, "Mumbai, Maharashtra, India"),
        "Bangalore" to PlaceResult("Bangalore", 12.9716, 77.5946, "Bangalore, Karnataka, India"),
        "Bengaluru" to PlaceResult("Bengaluru", 12.9716, 77.5946, "Bengaluru, Karnataka, India"),
        "Chennai" to PlaceResult("Chennai", 13.0827, 80.2707, "Chennai, Tamil Nadu, India"),
        "Kolkata" to PlaceResult("Kolkata", 22.5726, 88.3639, "Kolkata, West Bengal, India"),
        "Hyderabad" to PlaceResult("Hyderabad", 17.3850, 78.4867, "Hyderabad, Telangana, India"),
        
        // Major Cities
        "Pune" to PlaceResult("Pune", 18.5204, 73.8567, "Pune, Maharashtra, India"),
        "Ahmedabad" to PlaceResult("Ahmedabad", 23.0225, 72.5714, "Ahmedabad, Gujarat, India"),
        "Jaipur" to PlaceResult("Jaipur", 26.9124, 75.7873, "Jaipur, Rajasthan, India"),
        "Surat" to PlaceResult("Surat", 21.1702, 72.8311, "Surat, Gujarat, India"),
        "Lucknow" to PlaceResult("Lucknow", 26.8467, 80.9462, "Lucknow, Uttar Pradesh, India"),
        "Kanpur" to PlaceResult("Kanpur", 26.4499, 80.3319, "Kanpur, Uttar Pradesh, India"),
        "Nagpur" to PlaceResult("Nagpur", 21.1458, 79.0882, "Nagpur, Maharashtra, India"),
        "Indore" to PlaceResult("Indore", 22.7196, 75.8577, "Indore, Madhya Pradesh, India"),
        "Thane" to PlaceResult("Thane", 19.2183, 72.9781, "Thane, Maharashtra, India"),
        "Bhopal" to PlaceResult("Bhopal", 23.2599, 77.4126, "Bhopal, Madhya Pradesh, India"),
        "Visakhapatnam" to PlaceResult("Visakhapatnam", 17.6868, 83.2185, "Visakhapatnam, Andhra Pradesh, India"),
        "Patna" to PlaceResult("Patna", 25.5941, 85.1376, "Patna, Bihar, India"),
        "Vadodara" to PlaceResult("Vadodara", 22.3072, 73.1812, "Vadodara, Gujarat, India"),
        "Ludhiana" to PlaceResult("Ludhiana", 30.9010, 75.8573, "Ludhiana, Punjab, India"),
        "Agra" to PlaceResult("Agra", 27.1767, 78.0081, "Agra, Uttar Pradesh, India"),
        "Nashik" to PlaceResult("Nashik", 19.9975, 73.7898, "Nashik, Maharashtra, India"),
        "Meerut" to PlaceResult("Meerut", 28.9845, 77.7064, "Meerut, Uttar Pradesh, India"),
        "Rajkot" to PlaceResult("Rajkot", 22.3039, 70.8022, "Rajkot, Gujarat, India"),
        "Varanasi" to PlaceResult("Varanasi", 25.3176, 82.9739, "Varanasi, Uttar Pradesh, India"),
        "Srinagar" to PlaceResult("Srinagar", 34.0837, 74.7973, "Srinagar, Jammu and Kashmir, India"),
        "Aurangabad" to PlaceResult("Aurangabad", 19.8762, 75.3433, "Aurangabad, Maharashtra, India"),
        "Amritsar" to PlaceResult("Amritsar", 31.6340, 74.8723, "Amritsar, Punjab, India"),
        
        // State Capitals
        "Thiruvananthapuram" to PlaceResult("Thiruvananthapuram", 8.5241, 76.9366, "Thiruvananthapuram, Kerala, India"),
        "Gandhinagar" to PlaceResult("Gandhinagar", 23.2156, 72.6369, "Gandhinagar, Gujarat, India"),
        "Panaji" to PlaceResult("Panaji", 15.4909, 73.8278, "Panaji, Goa, India"),
        "Shimla" to PlaceResult("Shimla", 31.1048, 77.1734, "Shimla, Himachal Pradesh, India"),
        "Ranchi" to PlaceResult("Ranchi", 23.3441, 85.3096, "Ranchi, Jharkhand, India"),
        "Bhubaneswar" to PlaceResult("Bhubaneswar", 20.2961, 85.8245, "Bhubaneswar, Odisha, India"),
        "Chandigarh" to PlaceResult("Chandigarh", 30.7333, 76.7794, "Chandigarh, India"),
        "Dehradun" to PlaceResult("Dehradun", 30.3165, 78.0322, "Dehradun, Uttarakhand, India"),
        "Raipur" to PlaceResult("Raipur", 21.2514, 81.6296, "Raipur, Chhattisgarh, India"),
        
        // Popular Areas and Landmarks
        "Connaught Place" to PlaceResult("Connaught Place", 28.6315, 77.2167, "Connaught Place, New Delhi, India"),
        "Marine Drive" to PlaceResult("Marine Drive", 18.9441, 72.8262, "Marine Drive, Mumbai, Maharashtra, India"),
        "MG Road Bangalore" to PlaceResult("MG Road", 12.9759, 77.6037, "MG Road, Bangalore, Karnataka, India"),
        "Anna Nagar" to PlaceResult("Anna Nagar", 13.0878, 80.2085, "Anna Nagar, Chennai, Tamil Nadu, India"),
        "Park Street" to PlaceResult("Park Street", 22.5448, 88.3426, "Park Street, Kolkata, West Bengal, India"),
        "Banjara Hills" to PlaceResult("Banjara Hills", 17.4126, 78.4482, "Banjara Hills, Hyderabad, Telangana, India"),
        "Koregaon Park" to PlaceResult("Koregaon Park", 18.5362, 73.8980, "Koregaon Park, Pune, Maharashtra, India"),
        "Satellite" to PlaceResult("Satellite", 23.0395, 72.5066, "Satellite, Ahmedabad, Gujarat, India"),
        "Pink City" to PlaceResult("Pink City", 26.9124, 75.7873, "Pink City, Jaipur, Rajasthan, India"),
        
        // Additional Major Cities
        "Coimbatore" to PlaceResult("Coimbatore", 11.0168, 76.9558, "Coimbatore, Tamil Nadu, India"),
        "Kochi" to PlaceResult("Kochi", 9.9312, 76.2673, "Kochi, Kerala, India"),
        "Mysore" to PlaceResult("Mysore", 12.2958, 76.6394, "Mysore, Karnataka, India"),
        "Guwahati" to PlaceResult("Guwahati", 26.1445, 91.7362, "Guwahati, Assam, India"),
        "Jodhpur" to PlaceResult("Jodhpur", 26.2389, 73.0243, "Jodhpur, Rajasthan, India"),
        "Madurai" to PlaceResult("Madurai", 9.9252, 78.1198, "Madurai, Tamil Nadu, India"),
        "Vijayawada" to PlaceResult("Vijayawada", 16.5062, 80.6480, "Vijayawada, Andhra Pradesh, India"),
        "Jabalpur" to PlaceResult("Jabalpur", 23.1815, 79.9864, "Jabalpur, Madhya Pradesh, India")
    )

    /**
     * Fast, non-blocking place search using predefined data
     */
    suspend fun searchPlaces(
        context: Context,
        query: String,
        maxResults: Int = 5
    ): List<PlaceResult> = withContext(Dispatchers.Default) {
        
        Log.d(TAG, "Searching for: $query")
        
        if (query.length < 2) {
            return@withContext emptyList()
        }
        
        val searchQuery = query.lowercase().trim()
        val results = mutableListOf<PlaceResult>()
        
        // Search through predefined cities
        for ((cityName, placeResult) in indianCities) {
            if (cityName.lowercase().contains(searchQuery) || 
                placeResult.address.lowercase().contains(searchQuery)) {
                results.add(placeResult)
                
                if (results.size >= maxResults) {
                    break
                }
            }
        }
        
        Log.d(TAG, "Found ${results.size} results for: $query")
        return@withContext results
    }

    /**
     * Get coordinates for a specific place name
     */
    suspend fun getCoordinatesForPlace(
        context: Context,
        placeName: String
    ): PlaceResult? = withContext(Dispatchers.Default) {
        
        val searchName = placeName.lowercase().trim()
        
        // Direct match first
        indianCities[placeName]?.let { return@withContext it }
        
        // Fuzzy match
        for ((cityName, placeResult) in indianCities) {
            if (cityName.lowercase().contains(searchName)) {
                return@withContext placeResult
            }
        }
        
        return@withContext null
    }

    /**
     * Get address from coordinates (simplified version)
     */
    suspend fun getAddressFromCoordinates(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String? = withContext(Dispatchers.Default) {
        
        // Find closest city from predefined list
        var closestCity: PlaceResult? = null
        var minDistance = Double.MAX_VALUE
        
        for ((_, placeResult) in indianCities) {
            val distance = calculateDistance(
                latitude, longitude,
                placeResult.latitude, placeResult.longitude
            )
            
            if (distance < minDistance) {
                minDistance = distance
                closestCity = placeResult
            }
        }
        
        return@withContext closestCity?.name
    }

    /**
     * Calculate distance between two coordinates (Haversine formula)
     */
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // Earth radius in kilometers
        
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) +
                kotlin.math.cos(Math.toRadians(lat1)) * kotlin.math.cos(Math.toRadians(lat2)) *
                kotlin.math.sin(dLon / 2) * kotlin.math.sin(dLon / 2)
        
        val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
        
        return earthRadius * c
    }

    /**
     * Get popular cities list
     */
    fun getPopularCities(): List<String> {
        return indianCities.keys.toList()
    }

    /**
     * Filter popular cities based on search query
     */
    fun filterPopularCities(query: String): List<String> {
        if (query.length < 2) return emptyList()
        
        return indianCities.keys.filter { city ->
            city.lowercase().contains(query.lowercase())
        }.take(5)
    }
}